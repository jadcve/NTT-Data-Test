-- Creación de la tabla Usuario

CREATE TABLE `usuario` (
  `isactive` bit(1) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `last_login` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5171l57faosmj8myawaucatdw` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Creación de la tabla Telefono 

CREATE TABLE `telefono` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `usuario_id` bigint(20) DEFAULT NULL,
  `citycode` varchar(255) NOT NULL,
  `countrycode` varchar(255) NOT NULL,
  `number` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpi2c7iq0lw09d1ovc7bn86f85` (`usuario_id`),
  CONSTRAINT `FKpi2c7iq0lw09d1ovc7bn86f85` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Creación de la tabla JwtToken

CREATE TABLE `jwt_tokens` (
  `token_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `token` text NOT NULL,
  PRIMARY KEY (`token_id`),
  KEY `FKb7789nrs7v6hn3o8le5umbpk2` (`user_id`),
  CONSTRAINT `FKb7789nrs7v6hn3o8le5umbpk2` FOREIGN KEY (`user_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;