#!/bin/bash

source functions.sh

function ensure_java() {
	log_debug "Ensure java version is greater or equals to 8..."
	local java_version=$(java -version 2>&1 | head -n 1 | awk -F '"' '{print $2}')

	if [[ ${java_version:2:1} -lt 8 ]]; then
		log_error "You must have Java 8 at least. Your current version is $java_version."
		exit 0
	fi
}

function ensure_git() {
	log_debug "Ensure git is installed..."
	if [[ ! $(git --version) ]]; then
		log_error "You must have a valid git install."
		exit 0
	fi
}

function ensure_ruby() {
	if [[ ! $(ruby --version) ]]; then
		log_debug "You must have a valid ruby install."
		exit 0
	fi
}

function ensure_homebrew() {
	if [[ ! $(which brew) ]]; then
		log_debug "Installing Homebrew..."
		ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
	else
		log_debug "Updating Homebrew..."
		brew update
	fi
}

function ensure_mysql() {
	if [[ ! $(type -a mysql) ]]; then
		log_debug "Installing mysql..."
		brew install mysql
		mysql_install_db --verbose --user=`whoami` --basedir="$(brew --prefix mysql)" --datadir=/usr/local/var/mysql --tmpdir=/tmp
		cp `brew --prefix mysql`/homebrew.mxcl.mysql.plist ~/Library/LaunchAgents/
		launchctl load -w ~/Library/LaunchAgents/homebrew.mxcl.mysql.plist
	fi
	mysql=1
}

function ensure_tomcat8() {
	if [[ ! $(ls /usr/local/tomcat) ]]; then 
		log_debug "Installing wget for mac..."
		brew install wget
		log_debug "Fetching tomcat archive..."
		wget "http://apache.websitebeheerjd.nl/tomcat/tomcat-8/v8.0.32/bin/apache-tomcat-8.0.32.tar.gz"
		tar xvzf apache-tomcat-8.0.32.tar.gz
		log_debug "Installing Tomcat8 on /usr/local/tomcat8..."
		sudo mv apache-tomcat-8.0.32 /usr/local/apache-tomcat-8.0.32
		rm -rf apache-tomcat-8.0.32
	fi

	log_debug "Creating symbolic link for /Library/Tomcat"
	sudo rm -f /Library/Tomcat
	sudo ln -s /usr/local/apache-tomcat-8.0.32 /Library/Tomcat
	sudo chown -R $(whoami) /Library/Tomcat
	sudo chmod +x /Library/Tomcat/bin/*.sh
	sh /Library/Tomcat/bin/startup.sh
}

function clone_repository() {
	log_debug "Cloning mg-back repository to $(pwd) directory..."
	git clone git@bitbucket.org:gvstave/mg-back.git
	cd mg-back
	log_debug "Go to develop branch..."
	git checkout develop
	log_debug "Deploy Maven artifacts..."
	./deploynow.sh
}

ensure_java
ensure_git
ensure_ruby
ensure_homebrew
ensure_mysql
ensure_tomcat8

clone_repository
