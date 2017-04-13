-- phpMyAdmin SQL Dump
-- version 4.2.5
-- http://www.phpmyadmin.net
--
-- Client :  localhost:3306
-- Généré le :  Sam 05 Mars 2016 à 00:17
-- Version du serveur :  5.5.38
-- Version de PHP :  5.5.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Base de données :  `mistergift`
--
CREATE DATABASE IF NOT EXISTS `mistergift` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `mistergift`;

-- --------------------------------------------------------

--
-- Structure de la table `file_metadata`
--

DROP TABLE IF EXISTS `file_metadata`;
CREATE TABLE `file_metadata` (
`id` bigint(20) NOT NULL,
  `url` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Structure de la table `gift`
--

DROP TABLE IF EXISTS `gift`;
CREATE TABLE `gift` (
`id` bigint(20) NOT NULL,
  `brand` varchar(75) DEFAULT NULL,
  `creation_date` creationDate NOT NULL,
  `description` longtext,
  `modification_date` creationDate NOT NULL,
  `name` varchar(75) NOT NULL,
  `reference` varchar(25) DEFAULT NULL,
  `picture_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `gift_user_group`
--

DROP TABLE IF EXISTS `gift_user_group`;
CREATE TABLE `gift_user_group` (
  `gift_id` bigint(20) NOT NULL,
  `groups_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `links`
--

DROP TABLE IF EXISTS `links`;
CREATE TABLE `links` (
`id` bigint(20) NOT NULL,
  `url` varchar(255) NOT NULL,
  `gift_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `token`
--

DROP TABLE IF EXISTS `token`;
CREATE TABLE `token` (
  `id` varchar(75) NOT NULL,
  `expire_at` creationDate NOT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
`id` bigint(20) NOT NULL,
  `email` varchar(150) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` int(11) NOT NULL,
  `picture_id` bigint(20) DEFAULT NULL,
  `thumbnail_id` bigint(20) DEFAULT NULL,
  `token_id` varchar(75) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

-- --------------------------------------------------------

--
-- Structure de la table `users_user_group`
--

DROP TABLE IF EXISTS `users_user_group`;
CREATE TABLE `users_user_group` (
  `users_id` bigint(20) NOT NULL,
  `groups_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `user_group`
--

DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
`id` bigint(20) NOT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(75) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `user_group_users`
--

DROP TABLE IF EXISTS `user_group_users`;
CREATE TABLE `user_group_users` (
  `user_group_id` bigint(20) NOT NULL,
  `administrators_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Index pour les tables exportées
--

--
-- Index pour la table `file_metadata`
--
ALTER TABLE `file_metadata`
 ADD PRIMARY KEY (`id`), ADD KEY `FK_j37cb0xqyyo6do6ew94sek9rj` (`user_id`);

--
-- Index pour la table `gift`
--
ALTER TABLE `gift`
 ADD PRIMARY KEY (`id`), ADD KEY `FK_35j569kgl6503bborddyr08fg` (`picture_id`);

--
-- Index pour la table `gift_user_group`
--
ALTER TABLE `gift_user_group`
 ADD KEY `FK_7vplkmg0ais0s40rqtfyhh5hm` (`groups_id`), ADD KEY `FK_jgx4hg4lyy03kl9khli5rbxy9` (`gift_id`);

--
-- Index pour la table `links`
--
ALTER TABLE `links`
 ADD PRIMARY KEY (`id`), ADD KEY `FK_nghu3dlglfwr2rwnmcvtvba2e` (`gift_id`);

--
-- Index pour la table `token`
--
ALTER TABLE `token`
 ADD PRIMARY KEY (`id`), ADD KEY `FK_g7im3j7f0g31yhl6qco2iboy5` (`user_id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`), ADD KEY `FK_b8f1qvjjhpbvq8bsm6oh5ro81` (`picture_id`), ADD KEY `FK_e8xd6tjf4fgnq0xcwg8bktkvm` (`token_id`), ADD KEY `FK_ziodhfzeoihfoezifheziofzef_idx` (`thumbnail_id`);

--
-- Index pour la table `users_user_group`
--
ALTER TABLE `users_user_group`
 ADD UNIQUE KEY `UK_6f41bdkt628i5appsqb3q1jbw` (`groups_id`), ADD KEY `FK_n0208q2kc7ecgw8xxvgor2dd2` (`users_id`);

--
-- Index pour la table `user_group`
--
ALTER TABLE `user_group`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `user_group_users`
--
ALTER TABLE `user_group_users`
 ADD UNIQUE KEY `UK_oi082tqjv1wq7hvcckmadnn94` (`administrators_id`), ADD KEY `FK_9jib0g899h0gy3dypo7datfm9` (`user_group_id`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `file_metadata`
--
ALTER TABLE `file_metadata`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT pour la table `gift`
--
ALTER TABLE `gift`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `links`
--
ALTER TABLE `links`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT pour la table `user_group`
--
ALTER TABLE `user_group`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `file_metadata`
--
ALTER TABLE `file_metadata`
ADD CONSTRAINT `FK_j37cb0xqyyo6do6ew94sek9rj` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `gift`
--
ALTER TABLE `gift`
ADD CONSTRAINT `FK_35j569kgl6503bborddyr08fg` FOREIGN KEY (`picture_id`) REFERENCES `file_metadata` (`id`);

--
-- Contraintes pour la table `gift_user_group`
--
ALTER TABLE `gift_user_group`
ADD CONSTRAINT `FK_7vplkmg0ais0s40rqtfyhh5hm` FOREIGN KEY (`groups_id`) REFERENCES `user_group` (`id`),
ADD CONSTRAINT `FK_jgx4hg4lyy03kl9khli5rbxy9` FOREIGN KEY (`gift_id`) REFERENCES `gift` (`id`);

--
-- Contraintes pour la table `links`
--
ALTER TABLE `links`
ADD CONSTRAINT `FK_nghu3dlglfwr2rwnmcvtvba2e` FOREIGN KEY (`gift_id`) REFERENCES `gift` (`id`);

--
-- Contraintes pour la table `token`
--
ALTER TABLE `token`
ADD CONSTRAINT `FK_g7im3j7f0g31yhl6qco2iboy5` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `users`
--
ALTER TABLE `users`
ADD CONSTRAINT `FK_ziodhfzeoihfoezifheziofzef` FOREIGN KEY (`thumbnail_id`) REFERENCES `file_metadata` (`id`),
ADD CONSTRAINT `FK_b8f1qvjjhpbvq8bsm6oh5ro81` FOREIGN KEY (`picture_id`) REFERENCES `file_metadata` (`id`),
ADD CONSTRAINT `FK_e8xd6tjf4fgnq0xcwg8bktkvm` FOREIGN KEY (`token_id`) REFERENCES `token` (`id`);

--
-- Contraintes pour la table `users_user_group`
--
ALTER TABLE `users_user_group`
ADD CONSTRAINT `FK_6f41bdkt628i5appsqb3q1jbw` FOREIGN KEY (`groups_id`) REFERENCES `user_group` (`id`),
ADD CONSTRAINT `FK_n0208q2kc7ecgw8xxvgor2dd2` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `user_group_users`
--
ALTER TABLE `user_group_users`
ADD CONSTRAINT `FK_9jib0g899h0gy3dypo7datfm9` FOREIGN KEY (`user_group_id`) REFERENCES `user_group` (`id`),
ADD CONSTRAINT `FK_oi082tqjv1wq7hvcckmadnn94` FOREIGN KEY (`administrators_id`) REFERENCES `users` (`id`);
