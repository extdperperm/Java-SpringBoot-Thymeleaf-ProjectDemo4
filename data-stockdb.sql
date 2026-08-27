
DROP DATABASE IF EXISTS `stockdb`;

CREATE DATABASE `stockdb`;

DROP TABLE IF EXISTS `stockdb`.`tipoarticulo`;

CREATE TABLE `stockdb`.`tipoarticulo` (
  `idtipoarticulo_tar` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Clave primaria',
  `descripcion_tar` varchar(250) NOT NULL COMMENT 'Descripción del tipo de artículo.',
  `finsercion_tar` datetime DEFAULT current_timestamp(),
  `fultmodf_tar` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`idtipoarticulo_tar`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;


LOCK TABLES `stockdb`.`tipoarticulo` WRITE;

INSERT INTO `stockdb`.`tipoarticulo` VALUES (1,'Textiles','2022-05-07 20:35:41','2022-05-07 20:35:41'),(2,'Deporte','2022-05-07 20:36:56','2022-05-07 20:36:56'),(3,'Jardineria','2022-05-07 20:37:27','2022-05-07 20:37:27'),(4,'Jugueteria','2022-05-07 20:37:56','2022-05-07 20:37:56'),(5,'Alimentación','2022-05-07 20:38:18','2022-05-07 20:38:18'),(6,'Informática y Electrónica','2022-05-07 20:44:57','2022-05-07 20:44:57');

UNLOCK TABLES;

DROP TABLE IF EXISTS `stockdb`.`articulo`;

CREATE TABLE `stockdb`.`articulo` (
  `idarticulo_art` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Clave primaria del artículo.',
  `codbarras_art` int(12) NOT NULL COMMENT 'Clave única o alternativa del artículo.',
  `nombre_art` varchar(100) NOT NULL COMMENT 'Nombre del artículo.',
  `descripcion_art` varchar(250) DEFAULT NULL COMMENT 'Descripción extendida del artículo.',
  `idtipoarticulo_art` int(11) NOT NULL COMMENT 'Clave foranea de tipoarticulo.',
  `finsercion_art` datetime DEFAULT current_timestamp() COMMENT 'Fecha de inserción del registro.',
  `fultmodf_art` datetime DEFAULT current_timestamp() COMMENT 'Fecha de última modificación del registro.',
  PRIMARY KEY (`idarticulo_art`),
  UNIQUE KEY `UK1_ARTICULO` (`codbarras_art`),
  KEY `FK1_articulo` (`idtipoarticulo_art`),
  CONSTRAINT `FK1_articulo` FOREIGN KEY (`idtipoarticulo_art`) REFERENCES `tipoarticulo` (`idtipoarticulo_tar`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;


LOCK TABLES `stockdb`.`articulo` WRITE;

INSERT INTO `stockdb`.`articulo` VALUES (1,1223234300,'SAMSUNG GALAXY S21 FE 5G 8 GB','Samsung Galaxy S21 FE 5G 8 GB + 256 GB negro móvil libre. Pantalla inteligente de alta resolución.',6,'2022-05-07 20:47:13','2022-05-07 20:47:13'),(2,1110229391,'Xiaomi Redmi Note 11 4 GB + 128 GB Star Blue móvil libre.','Cuenta con un el nuevo procesador Snapdragon 680, aportando un gran rendimiento.\r\n\r\nNueva pantalla de 90Hz para poder disfrutar de todo el contenido de series y películas.',6,'2022-05-07 20:50:12','2022-05-07 20:50:12'),(3,223300292,'Camiseta algodón estampado','Camiseta algodón estampado',1,'2022-05-11 00:20:02','2022-05-11 00:20:02'),(4,1232322332,'Bermudas denim algodón','Bermudas denim algodón',1,'2022-05-11 00:21:19','2022-05-11 00:21:19'),(5,122233223,'Bicicleta de niños 20\'\' B-PRO','Bicicleta 20\'\' para niños, con cuadro de aluminio 6061. y 6 velocidades.\r\n\r\nEdad recomendada 6-9 años (Altura 120-135 cm).',4,'2022-05-11 00:22:57','2022-05-11 00:22:57'),(6,1233433323,'Tobogán Gigante Little Tikes','Divertidísimo tobogán. Anchos escalones y asas integradas para mejorar el apoyo y seguridad de los pequeños de casa.\r\n\r\nFácil montaje y desmontaje para guardarlo cómodamente.',4,'2022-05-11 00:24:22','2022-05-11 00:24:22'),(7,233234434,'Portería de fútbol kipsta SG 500 talla L azul marino/naranja','Los diseñadores futbolistas han concebido la portería de fútbol SG500 en acero/aluminio. Con escuadras para percibir las mismas sensaciones que los futbolistas.',2,'2022-05-11 00:26:27','2022-05-11 00:26:27'),(8,322112212,'Leche Semidesnatada Asturiana','Leche Semidesnatada Asturiana',5,'2022-05-11 00:29:13','2022-05-11 00:29:13'),(9,12234434,'Piscina chapa 3x1,20','Piscina chapa 3x1,20',3,'2022-05-11 00:29:43','2022-05-11 00:29:43');

UNLOCK TABLES;



