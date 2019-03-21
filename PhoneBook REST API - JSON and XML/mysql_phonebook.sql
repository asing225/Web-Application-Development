-- Create and use database
create database phonebook_rest;
use phonebook_rest;

-- Create phonebook table
CREATE TABLE `phonebook` (
  `phonebook_no` int,
  `firstname` text,
  `lastname` text,
  `phone` int,
  PRIMARY KEY (`phone`)
);

-- Insert into phonebook
INSERT INTO phonebook (firstname, lastname, phone) VALUES 
		('King','Kong', 1234),
		('Black','Panther', 5678),
		('The','Hulk', 9012),
		('Black','Widow', 3456),
		('Captain','America', 7890),
		('Ant','Man', 1357);
		
COMMIT;