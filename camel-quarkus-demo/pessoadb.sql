use pessoadb;

use pessoadb;

CREATE TABLE `tb_pessoa` (
  `pessoaId` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `tipo_pessoa` varchar(15) DEFAULT NULL,
  `cpf_cnpj` varchar(18) NOT NULL,
  `verification_code` varchar(45) NOT NULL,
  `verification_date` varchar(19) NOT NULL,
  PRIMARY KEY (`pessoaId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

drop table  tb_pessoa

