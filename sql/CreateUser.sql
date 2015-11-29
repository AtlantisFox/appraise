GRANT USAGE ON *.* TO 'appraise'@'localhost';
DROP USER 'appraise'@'localhost';
CREATE USER 'appraise'@'localhost' IDENTIFIED BY 'Raise123';
GRANT USAGE ON *.* TO 'appraise'@'localhost';
GRANT SELECT, DELETE, INSERT, UPDATE  ON `appraise`.* TO 'appraise'@'localhost';
FLUSH PRIVILEGES;
