-- Crear base de datos
-- Create database crypto;
USE crypto;

-- Tabla de usuarios
CREATE TABLE usuario (
    cedula BIGINT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    contraseña varchar(100)NOT NULL, 
    fecha_nac DATE NOT NULL,
    pais_nacimiento VARCHAR(50) NOT NULL
);

-- Tabla de billeteras (un usuario puede tener varias)
CREATE TABLE billetera (
    numero_billetera VARCHAR(20) PRIMARY KEY,
    nombre varchar(100) NOT NULL,
    cedula_usuario BIGINT NOT NULL,
    FOREIGN KEY (cedula_usuario) REFERENCES usuario(cedula)
);

-- Tabla de monedas (catálogo de criptos)
CREATE TABLE moneda (
    id_moneda INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    simbolo VARCHAR(10) NOT NULL UNIQUE,
    cantidad float 
);

-- Relación billetera <-> monedas (saldo de cada moneda en la billetera)
CREATE TABLE billetera_moneda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_billetera VARCHAR(20) NOT NULL ,
    id_moneda INT NOT NULL,
    cantidad DECIMAL(18,8) DEFAULT 0,
    FOREIGN KEY (numero_billetera) REFERENCES billetera(numero_billetera),
    FOREIGN KEY (id_moneda) REFERENCES moneda(id_moneda),
    UNIQUE (numero_billetera, id_moneda) -- evita duplicados
);
