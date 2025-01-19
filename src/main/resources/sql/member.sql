USE platform;


DESC member;


SELECT * FROM member;


-- UPDATE member
-- SET role = 'ADMIN'
-- WHERE member_id = 1;


-- DELETE FROM member;


INSERT INTO member (
    created_at, member_id, police_unit_id, office_phone, person_phone, member_name, email, password, role
) VALUES
      (NOW(), 1, 1, NULL, '010-4773-5141', '관리자', 'admin@email.com', '$2a$10$ZWm0tSioCSg1E8COGsNNN.gF.qzXGNfbcwiwAgIzgiXMw32j9jeWu', 'ADMIN'),
      (NOW(), 2, 2, NULL, '010-1234-5678', 'user1', 'user1@email.com', '$2a$10$yUxTiZfXIQ0k.Btw6ewEcOkeFV.h0Jk64/0xL0QPHScNCW3CVj0SO', 'USER'),
      (NOW(), 3, 3, NULL, '010-1234-5678', 'user2', 'user2@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER'),
      (NOW(), 4, 4, NULL, '010-1234-5678', 'user3', 'user3@email.com', '$2a$10$mstjo6AH0wWQtkca2kAshO9NIiNaKj3aWkWWuGmgmECpEoUwUofvG', 'USER');





INSERT INTO member (
    created_at, member_id, police_unit_id, office_phone, person_phone, member_name, email, password, role
) VALUES
      (NOW(), 5, 5, NULL, '010-4773-5141', '관리자', 'admin2@email.com', '$2a$10$ZWm0tSioCSg1E8COGsNNN.gF.qzXGNfbcwiwAgIzgiXMw32j9jeWu', 'ADMIN'),
      (NOW(), 6, 6, NULL, '010-1234-5678', 'user1', 'user6@email.com', '$2a$10$yUxTiZfXIQ0k.Btw6ewEcOkeFV.h0Jk64/0xL0QPHScNCW3CVj0SO', 'USER'),
      (NOW(), 7, 7, NULL, '010-1234-5678', 'user2', 'user7@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER'),
      (NOW(), 8, 8, NULL, '010-1234-5678', 'user3', 'user8@email.com', '$2a$10$mstjo6AH0wWQtkca2kAshO9NIiNaKj3aWkWWuGmgmECpEoUwUofvG', 'USER');
