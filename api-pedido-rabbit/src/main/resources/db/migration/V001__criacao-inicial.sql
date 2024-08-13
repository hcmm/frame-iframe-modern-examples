CREATE TABLE Cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    email VARCHAR(255),
    endereco VARCHAR(255)
);

CREATE TABLE Pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255),
    valor DOUBLE,
    cliente_id BIGINT,
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);
