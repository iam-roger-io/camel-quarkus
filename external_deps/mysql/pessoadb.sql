use pessoadb;

use pessoadb;

CREATE TABLE `tb_pessoa` (
  `pessoaId` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `tipo_pessoa` varchar(15) DEFAULT NULL,
  `cpf_cnpj` varchar(18) NOT NULL,
  `status` varchar(100) NOT NULL,
  `status_message` varchar(5000) DEFAULT NULL,
  `verification_code` varchar(45) NOT NULL,
  `verification_date` varchar(19) NOT NULL,
  PRIMARY KEY (`pessoaId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


