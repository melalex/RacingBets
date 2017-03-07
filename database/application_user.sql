DELIMITER //

CREATE PROCEDURE add_role(IN application_user_id INT UNSIGNED, IN role_name ENUM('Handicapper', 'Bookmaker', 'Admin'))
  BEGIN
    DECLARE is_exists BOOL;
    SET is_exists = EXISTS(
        SELECT * FROM role WHERE role.application_user_id = application_user_id AND role.name = role_name
    );

    CASE
      WHEN is_exists = FALSE THEN
        INSERT INTO role (application_user_id, name) VALUES (application_user_id, role_name);
    END CASE;
  END; //