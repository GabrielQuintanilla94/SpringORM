-- 1. CREACIÓN DE LA BASE DE DATOS
DROP DATABASE IF EXISTS tiendita_caluco;
CREATE DATABASE tiendita_caluco;
USE tiendita_caluco;

-- 2. TABLA USUARIOS
CREATE TABLE Usuario (
                         id_usuario INT AUTO_INCREMENT PRIMARY KEY,
                         nombre_usuario VARCHAR(100) NOT NULL,
                         usuario_login VARCHAR(30) NOT NULL UNIQUE,
                         contrasena VARCHAR(255) NOT NULL,
                         rol ENUM('Administrador','Empleado','Supervisor') NOT NULL,
                         estado TINYINT DEFAULT 1
);

-- 3. TABLA CATEGORÍAS
CREATE TABLE Categoria (
                           id_categoria INT AUTO_INCREMENT PRIMARY KEY,
                           nombre_categoria VARCHAR(50) NOT NULL,
                           descripcion VARCHAR(150),
                           estado TINYINT DEFAULT 1
);

-- 4. TABLA PRODUCTOS
CREATE TABLE Producto (
                          id_producto INT AUTO_INCREMENT PRIMARY KEY,
                          nombre_producto VARCHAR(100) NOT NULL,
                          id_categoria INT NOT NULL,
                          precio_compra DECIMAL(10,2) NOT NULL,
                          precio_venta DECIMAL(10,2) NOT NULL,
                          unidades_por_caja INT DEFAULT 1 NOT NULL, -- Campo clave para conversión
                          unidad_medida VARCHAR(20) NOT NULL,
                          imagen_url VARCHAR(255),
                          estado TINYINT DEFAULT 1,
                          FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria)
);

-- 5. TABLA INVENTARIO
CREATE TABLE Inventario (
                            id_inventario INT AUTO_INCREMENT PRIMARY KEY,
                            id_producto INT NOT NULL,
                            stock_actual INT NOT NULL,
                            stock_minimo INT NOT NULL DEFAULT 10,
                            fecha_actualizacion DATETIME NOT NULL,
                            FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)
);

-- 6. TABLA MOVIMIENTOS
CREATE TABLE Movimiento (
                            id_movimiento INT AUTO_INCREMENT PRIMARY KEY,
                            id_producto INT NOT NULL,
                            cantidad INT NOT NULL,
                            costo DECIMAL(10,2) DEFAULT 0.00, -- Campo para registrar dinero
                            tipo_movimiento ENUM('Entrada','Salida') NOT NULL, -- Normalizado a Mayúscula inicial
                            descripcion VARCHAR(200),
                            fecha_movimiento DATETIME NOT NULL,
                            id_usuario INT NOT NULL,
                            FOREIGN KEY (id_producto) REFERENCES Producto(id_producto),
                            FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

-- =======================================================
-- CARGA DE DATOS INICIALES (SEED DATA)
-- =======================================================

-- A. USUARIOS DEL SISTEMA
INSERT INTO Usuario (nombre_usuario, usuario_login, contrasena, rol, estado) VALUES
                                                                                 ('Dueño El Zapote', 'admin', 'admin123', 'Administrador', 1),
                                                                                 ('Juan Pérez', 'cajero', 'cajero123', 'Empleado', 1),
                                                                                 ('María López', 'super', 'super123', 'Supervisor', 1);

-- B. CATEGORÍAS
INSERT INTO Categoria (id_categoria, nombre_categoria, descripcion, estado) VALUES
                                                                                (1, 'Bebidas y Refrescos', 'Sodas, jugos, agua y energizantes', 1),
                                                                                (2, 'Snacks y Alimentos', 'Churritos, galletas, golosinas y boquitas', 1),
                                                                                (3, 'Abarrotes', 'Granos básicos, limpieza, higiene y canasta básica', 1);

-- C. PRODUCTOS (Catálogo inicial de 30 ítems)
-- Bebidas
INSERT INTO Producto (nombre_producto, id_categoria, precio_compra, precio_venta, unidades_por_caja, unidad_medida, imagen_url, estado) VALUES
                                                                                                                                            ('Coca Cola 1.25L', 1, 1.00, 1.25, 12, 'Botella', '', 1),
                                                                                                                                            ('Pepsi 3 Litros', 1, 1.90, 2.50, 6, 'Botella', '', 1),
                                                                                                                                            ('Agua Azul 600ml', 1, 0.35, 0.60, 24, 'Botella', '', 1),
                                                                                                                                            ('Jugo Kerns Durazno Lata', 1, 0.40, 0.65, 24, 'Lata', '', 1),
                                                                                                                                            ('Agua Perlitas 500ml', 1, 0.25, 0.50, 24, 'Botella', '', 1),
                                                                                                                                            ('Kolashampan 1.5L', 1, 1.10, 1.50, 12, 'Botella', '', 1),
                                                                                                                                            ('Powerade Azul', 1, 0.65, 1.00, 12, 'Botella', '', 1),
                                                                                                                                            ('Tropical Uva 1.25L', 1, 0.90, 1.25, 12, 'Botella', '', 1),
                                                                                                                                            ('Monster Energy', 1, 1.50, 2.25, 24, 'Lata', '', 1),
                                                                                                                                            ('Bolsa de Hielo', 1, 0.75, 1.25, 10, 'Bolsa', '', 1);

-- Snacks
INSERT INTO Producto (nombre_producto, id_categoria, precio_compra, precio_venta, unidades_por_caja, unidad_medida, imagen_url, estado) VALUES
                                                                                                                                            ('Diana Quesitos', 2, 0.12, 0.15, 24, 'Bolsa', '', 1),
                                                                                                                                            ('Diana Jalapeños', 2, 0.12, 0.15, 24, 'Bolsa', '', 1),
                                                                                                                                            ('Diana Elotitos', 2, 0.12, 0.15, 24, 'Bolsa', '', 1),
                                                                                                                                            ('Diana Palitos', 2, 0.12, 0.15, 24, 'Bolsa', '', 1),
                                                                                                                                            ('Bocadeli Gustitos', 2, 0.12, 0.15, 24, 'Bolsa', '', 1),
                                                                                                                                            ('Galleta Picnic Vainilla', 2, 0.20, 0.35, 12, 'Paquete', '', 1),
                                                                                                                                            ('Galleta Chiky Fresa', 2, 0.25, 0.40, 12, 'Paquete', '', 1),
                                                                                                                                            ('Churrito Típico', 2, 0.08, 0.10, 50, 'Bolsa', '', 1),
                                                                                                                                            ('Semilla de Marañón', 2, 0.35, 0.60, 20, 'Bolsa', '', 1),
                                                                                                                                            ('Chocolate Best', 2, 0.08, 0.15, 24, 'Barra', '', 1);

-- Abarrotes
INSERT INTO Producto (nombre_producto, id_categoria, precio_compra, precio_venta, unidades_por_caja, unidad_medida, imagen_url, estado) VALUES
                                                                                                                                            ('Arroz San Pedro', 3, 0.45, 0.60, 25, 'Libra', '', 1),
                                                                                                                                            ('Frijol Rojo de Seda', 3, 0.75, 1.00, 25, 'Libra', '', 1),
                                                                                                                                            ('Azúcar Morena', 3, 0.45, 0.55, 25, 'Libra', '', 1),
                                                                                                                                            ('Café Riko (Sobre)', 3, 0.10, 0.15, 50, 'Sobre', '', 1),
                                                                                                                                            ('Café Musun (Vaso)', 3, 0.15, 0.25, 12, 'Vaso', '', 1),
                                                                                                                                            ('Salsa Natura Tomate', 3, 0.25, 0.40, 24, 'Unidad', '', 1),
                                                                                                                                            ('Aceite Orisol', 3, 1.25, 1.75, 12, 'Botella', '', 1),
                                                                                                                                            ('Jabón Xtra Olor', 3, 0.60, 0.85, 6, 'Bola', '', 1),
                                                                                                                                            ('Papel Higiénico (Rollo)', 3, 0.30, 0.50, 48, 'Rollo', '', 1),
                                                                                                                                            ('Lysol Desinfectante', 3, 2.50, 3.75, 6, 'Bote', '', 1);