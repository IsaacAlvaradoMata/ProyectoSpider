-- 👤 Crear usuario SPIDER con contraseña 'spider'
CREATE
USER spider IDENTIFIED BY spider
DEFAULT TABLESPACE users
TEMPORARY TABLESPACE temp
QUOTA UNLIMITED ON users;

-- 🧠 Dar todos los privilegios (⚠️ solo recomendado en desarrollo)
GRANT DBA TO spider;
