PRAGMA foreign_keys = ON;

CREATE TABLE idiomas (
   nombre TEXT PRIMARY KEY
);

CREATE TABLE palabras (
   nombre TEXT,
   idioma TEXT,
   PRIMARY KEY(nombre, idioma),
   FOREIGN KEY(idioma) REFERENCES idiomas(nombre)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE traducciones (
   palabra TEXT,
   idioma_palabra  TEXT,
   traduccion TEXT,
   idioma_traduccion TEXT,
   PRIMARY KEY(palabra, idioma_palabra, idioma_traduccion, traduccion),
   FOREIGN KEY(palabra, idioma_palabra) REFERENCES palabras(nombre, idioma)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
   FOREIGN KEY(traduccion, idioma_traduccion) REFERENCES palabras(nombre, idioma)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE grupos (
   nombre  TEXT,
   descripcion TEXT,
   PRIMARY KEY(nombre)
);

CREATE TABLE agrupaciones (
   palabra  TEXT,
   idioma TEXT,
   grupo  TEXT,
   PRIMARY KEY(palabra, idioma, grupo),
   FOREIGN KEY(palabra, idioma) REFERENCES palabras(nombre, idioma)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
   FOREIGN KEY(grupo) REFERENCES grupos(nombre)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);


CREATE TRIGGER check_traduccion
BEFORE INSERT ON traducciones
FOR EACH ROW BEGIN
   SELECT RAISE(ABORT, 'No se puede traducir una palabra al mismo idioma.')
   WHERE NEW.idioma_palabra = NEW.idioma_traduccion;
END;

CREATE TRIGGER rellenar_traduccion 
AFTER INSERT ON traducciones
FOR EACH ROW BEGIN

   INSERT INTO traducciones
   SELECT NEW.traduccion, NEW.idioma_traduccion, NEW.palabra, NEW.idioma_palabra
   WHERE NEW.idioma_traduccion <> NEW.idioma_palabra;

   INSERT INTO traducciones 
   SELECT NEW.traduccion, NEW.idioma_traduccion, traduccion, idioma_traduccion
   FROM traducciones
   WHERE palabra = NEW.palabra AND traduccion <> NEW.traduccion AND
         idioma_traduccion <> NEW.idioma_traduccion

   UNION

   SELECT traduccion, idioma_traduccion, NEW.traduccion, NEW.idioma_traduccion
   FROM traducciones
   WHERE palabra = NEW.palabra AND traduccion <> NEW.traduccion AND
         idioma_traduccion <> NEW.idioma_traduccion;

END;


CREATE TRIGGER quitar_traduccion_cruzada
AFTER DELETE ON traducciones
FOR EACH ROw BEGIN
   DELETE FROM traducciones 
   WHERE palabra = OLD.traduccion AND traduccion = OLD.palabra AND
         idioma_palabra = OLD.idioma_traduccion AND
         idioma_traduccion = OLD.idioma_palabra;
END;

CREATE TRIGGER update_idioma_remove_traducciones
AFTER UPDATE OF idioma ON palabras
FOR EACH ROW WHEN OLD.idioma <> NEW.idioma
BEGIN
   DELETE FROM traducciones WHERE palabra = OLD.nombre AND idioma_palabra = OLD.idioma;
END;

