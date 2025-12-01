
CREATE DATABASE IF NOT EXISTS tiendita_caluco;
USE tiendita_caluco;

-- 2. CREAR LA TABLA CATEGORIA
CREATE TABLE IF NOT EXISTS Categoria (
    id_categoria BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    estado INT
);

-- 3. CREAR LA TABLA PRODUCTO

CREATE TABLE IF NOT EXISTS Producto (
    id_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_producto VARCHAR(255),
    precio_compra DECIMAL(10, 2),
    precio_venta DECIMAL(10, 2),
    unidad_medida VARCHAR(255),
    imagen_url VARCHAR(255),
    estado INT,
    id_categoria BIGINT,
    FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria)
);


INSERT INTO Categoria (nombre_categoria, descripcion, estado) VALUES 
('Electrónica', 'TVs, Celulares y más', 1),
('Hogar', 'Muebles y decoración', 1),
('Ropa', 'Moda para todos', 1);