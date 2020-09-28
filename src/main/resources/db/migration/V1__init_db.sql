create TABLE beers (
   id INT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(100) NOT NULL,
   ingredients VARCHAR(255),
   alcohol_content VARCHAR(50),
   price decimal(12,2) NOT NULL,
   category VARCHAR(101)
);

insert into beers (name, ingredients, alcohol_content, price, category) values
  ('Cerveja Colorado Amazonica 310Ml','Peixes, Queijos, Saladas', '4.5%', 9.69, 'Cerveja de Trigo');