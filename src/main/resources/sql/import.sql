USE platform;

select * from member;
select * from board;

INSERT INTO police_unit (dept_name, station_name, police_unit_name, police_unit_type, address, latitude, longitude)
VALUES
    ('KT', 'AIVLE', '프로젝트', 'ADMIN', '광주 북구 무등로202번길 15', 35.16443346, 126.90621829),
    ('광주청', '광주광산', '수완', 'DISTRICT', '광주광역시 광산구 수완로 63', 35.19093029, 126.8284667),
    ('광주청', '광주광산', '첨단', 'DISTRICT', '광주광역시 광산구 첨단중앙로106번길 65-15', 35.21631094, 126.8464821),
    ('광주청', '광주광산', '우산', 'DISTRICT', '광주광역시 광산구 사암로 106번길 3', 35.15387726, 126.8075954),
    ('광주청', '광주광산', '월곡', 'DISTRICT', '광주광역시 광산구 사암로 390번길 13-16', 35.1794465, 126.809131),
    ('광주청', '광주광산', '도산', 'OUTPOST', '광주광역시 광산구 상무대로 93', 35.12901277, 126.7854209),
    ('광주청', '광주광산', '송정', 'OUTPOST', '광주광역시 광산구 신흥신기안길 31-6', 35.1447119, 126.8031189),
    ('광주청', '광주광산', '하남', 'OUTPOST', '광주광역시 광산구 고봉로 124', 35.1847442, 126.794526),
    ('광주청', '광주광산', '비아', 'OUTPOST', '광주광역시 광산구 비아중앙로31번길 14', 35.22168892, 126.8238277),
    ('광주청', '광주광산', '평동', 'OUTPOST', '광주광역시 광산구 평동로 790-16', 35.12420777, 126.7597576),
    ('광주청', '광주광산', '동곡', 'OUTPOST', '광주광역시 광산구 동곡로 155', 35.09760031, 126.773814),
    ('광주청', '광주광산', '삼도', 'OUTPOST', '광주광역시 광산구 삼도로 341-3', 35.16326055, 126.700458),
    ('광주청', '광주광산', '본량', 'OUTPOST', '광주광역시 광산구 용진로 395', 35.18143386, 126.7304359),
    ('광주청', '광주광산', '임곡', 'OUTPOST', '광주광역시 광산구 하림길 1', 35.21983917, 126.7451898),
    ('광주청', '광주동부', '금남', 'DISTRICT', '광주광역시 동구 예술길 31-5', 35.14953086, 126.9195492),
    ('광주청', '광주동부', '학서', 'OUTPOST', '광주광역시 동구 양림로 119 번길 13', 35.14085902, 126.9199241),
    ('광주청', '광주동부', '산수', 'OUTPOST', '광주광역시 동구 필문대로 157', 35.15661715, 126.9320077),
    ('광주청', '광주동부', '지원', 'OUTPOST', '광주광역시 동구 남문로 570', 35.12210622, 126.9334451),
    ('광주청', '광주동부', '지산', 'OUTPOST', '광주광역시 동구 지호로 131', 35.14944122, 126.9427872),
    ('광주청', '광주서부', '금호', 'DISTRICT', '광주광역시 서구 운천로 30', 35.1353284, 126.859396),
    ('광주청', '광주서부', '상무', 'DISTRICT', '광주광역시 서구 상무연하로 106', 35.15776287, 126.8505491),
    ('광주청', '광주서부', '화정', 'DISTRICT', '광주광역시 서구 화운로 160', 35.15320247, 126.876943),
    ('광주청', '광주서부', '농성', 'OUTPOST', '광주광역시 서구 화정로 316', 35.15044724, 126.8903213),
    ('광주청', '광주서부', '동천', 'OUTPOST', '광주광역시 서구 유림로98번길 6', 35.17092756, 126.8644178),
    ('광주청', '광주서부', '염주', 'OUTPOST', '광주 서구 염화로134번길 18', 35.14159878, 126.8792537),
    ('광주청', '광주서부', '풍암', 'OUTPOST', '광주광역시 서구 풍암1로 33', 35.12439554, 126.8788899),
    ('광주청', '광주남부', '백운', 'DISTRICT', '광주광역시 남구 월산로 30', 35.14107025, 126.9006204),
    ('광주청', '광주남부', '방림', 'DISTRICT', '광주광역시 남구 대남대로149번길 8', 35.13297086, 126.9086179),
    ('광주청', '광주남부', '효덕', 'DISTRICT', '광주광역시 남구 화산로 27', 35.11206741, 126.8997427),
    ('광주청', '광주남부', '양림', 'OUTPOST', '광주광역시 남구 천변좌로 410', 35.14296998, 126.9152397),
    ('광주청', '광주남부', '주월', 'OUTPOST', '광주광역시 남구 회재로1217번길 7', 35.13395981, 126.8934637),
    ('광주청', '광주남부', '대촌', 'OUTPOST', '광주광역시 남구 고싸움로 136', 35.08023475, 126.8345574),
    ('광주청', '광주북부', '우산', 'DISTRICT', '광주광역시 북구 중문로 63', 35.17826799, 126.919836),
    ('광주청', '광주북부', '동운', 'DISTRICT', '광주광역시 북구 북문대로 240', 35.18711477, 126.8661408),
    ('광주청', '광주북부', '두암', 'DISTRICT', '광주광역시 북구 군왕로 146', 35.17416165, 126.9331553),
    ('광주청', '광주북부', '용봉', 'DISTRICT', '광주광역시 북구 문화소통로 78', 35.18343717, 126.9024981),
    ('광주청', '광주북부', '일곡', 'DISTRICT', '광주광역시 북구 설죽로 489', 35.20300417, 126.8981714),
    ('광주청', '광주북부', '문흥', 'DISTRICT', '광주광역시 북구 대천로 146', 35.187096, 126.9215437),
    ('광주청', '광주북부', '역전', 'DISTRICT', '광주광역시 북구 태봉로 57', 35.16256898, 126.9050367),
    ('광주청', '광주북부', '건국', 'DISTRICT', '광주광역시 북구 본촌마을길102번길 17', 35.21854545, 126.8796907),
    ('광주청', '광주북부', '석곡', 'OUTPOST', '광주광역시 북구 동문대로 642', 35.21388341, 126.9488323);




INSERT INTO member (
    created_at, member_id, police_unit_id, office_phone, person_phone, member_name, email, password, role
) VALUES
      (NOW(), 1, 1, NULL, '010-4773-5141', '관리자', 'admin@email.com', '$2a$10$ZWm0tSioCSg1E8COGsNNN.gF.qzXGNfbcwiwAgIzgiXMw32j9jeWu', 'ADMIN'),
      (NOW(), 2, 2, NULL, '010-1234-5678', '유저1', 'user1@email.com', '$2a$10$yUxTiZfXIQ0k.Btw6ewEcOkeFV.h0Jk64/0xL0QPHScNCW3CVj0SO', 'USER'),
      (NOW(), 3, 3, NULL, '010-1234-5678', '유저2', 'user2@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER'),
      (NOW(), 4, 4, NULL, '010-1234-5678', '유저3', 'user3@email.com', '$2a$10$mstjo6AH0wWQtkca2kAshO9NIiNaKj3aWkWWuGmgmECpEoUwUofvG', 'USER'),
      (NOW(), 5, 5, NULL, '010-1234-5678', '유저4', 'user4@email.com', '$2a$10$ZWm0tSioCSg1E8COGsNNN.gF.qzXGNfbcwiwAgIzgiXMw32j9jeWu', 'USER'),
      (NOW(), 6, 6, NULL, '010-1234-5678', '유저5', 'user5@email.com', '$2a$10$yUxTiZfXIQ0k.Btw6ewEcOkeFV.h0Jk64/0xL0QPHScNCW3CVj0SO', 'USER'),
      (NOW(), 7, 7, NULL, '010-1234-5678', '유저6', 'user6@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER'),
      (NOW(), 8, 8, NULL, '010-1234-5678', '유저7', 'user7@email.com', '$2a$10$mstjo6AH0wWQtkca2kAshO9NIiNaKj3aWkWWuGmgmECpEoUwUofvG', 'USER'),
      (NOW(), 9, 9, NULL, '010-1234-5678', '유저8', 'user8@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER'),
      (NOW(), 10, 10, NULL, '010-1234-5678', '유저9', 'user9@email.com', '$2a$10$mstjo6AH0wWQtkca2kAshO9NIiNaKj3aWkWWuGmgmECpEoUwUofvG', 'USER'),
      (NOW(), 11, 11, NULL, '010-1234-5678', '유저10', 'user10@email.com', '$2a$10$ZWm0tSioCSg1E8COGsNNN.gF.qzXGNfbcwiwAgIzgiXMw32j9jeWu', 'USER'),
      (NOW(), 12, 12, NULL, '010-1234-5678', '유저11', 'user11@email.com', '$2a$10$yUxTiZfXIQ0k.Btw6ewEcOkeFV.h0Jk64/0xL0QPHScNCW3CVj0SO', 'USER'),
      (NOW(), 13, 13, NULL, '010-1234-5678', '유저12', 'user12@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER'),
      (NOW(), 14, 14, NULL, '010-1234-5678', '유저13', 'user13@email.com', '$2a$10$mstjo6AH0wWQtkca2kAshO9NIiNaKj3aWkWWuGmgmECpEoUwUofvG', 'USER')
;

INSERT INTO member (
    created_at, member_id, police_unit_id, office_phone, person_phone, member_name, email, password, role
) VALUES
      (NOW(), 15, 15, NULL, '010-4773-5141', '관리자', 'admin@email.com', '$2a$10$ZWm0tSioCSg1E8COGsNNN.gF.qzXGNfbcwiwAgIzgiXMw32j9jeWu', 'ADMIN'),
      (NOW(), 16, 16, NULL, '010-1234-5678', '유저1', 'user1@email.com', '$2a$10$yUxTiZfXIQ0k.Btw6ewEcOkeFV.h0Jk64/0xL0QPHScNCW3CVj0SO', 'USER'),
      (NOW(), 17, 17, NULL, '010-1234-5678', '유저2', 'user2@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER'),
      (NOW(), 18, 4, NULL, '010-1234-5678', '유저3', 'user3@email.com', '$2a$10$mstjo6AH0wWQtkca2kAshO9NIiNaKj3aWkWWuGmgmECpEoUwUofvG', 'USER'),
      (NOW(), 19, 5, NULL, '010-1234-5678', '유저4', 'user4@email.com', '$2a$10$ZWm0tSioCSg1E8COGsNNN.gF.qzXGNfbcwiwAgIzgiXMw32j9jeWu', 'USER'),
      (NOW(), 20, 6, NULL, '010-1234-5678', '유저5', 'user5@email.com', '$2a$10$yUxTiZfXIQ0k.Btw6ewEcOkeFV.h0Jk64/0xL0QPHScNCW3CVj0SO', 'USER'),
      (NOW(), 21, 7, NULL, '010-1234-5678', '유저6', 'user6@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER'),
      (NOW(), 22, 8, NULL, '010-1234-5678', '유저7', 'user7@email.com', '$2a$10$mstjo6AH0wWQtkca2kAshO9NIiNaKj3aWkWWuGmgmECpEoUwUofvG', 'USER'),
      (NOW(), 23, 9, NULL, '010-1234-5678', '유저8', 'user8@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER'),
      (NOW(), 24, 10, NULL, '010-1234-5678', '유저9', 'user9@email.com', '$2a$10$mstjo6AH0wWQtkca2kAshO9NIiNaKj3aWkWWuGmgmECpEoUwUofvG', 'USER'),
      (NOW(), 25, 11, NULL, '010-1234-5678', '유저10', 'user10@email.com', '$2a$10$ZWm0tSioCSg1E8COGsNNN.gF.qzXGNfbcwiwAgIzgiXMw32j9jeWu', 'USER'),
      (NOW(), 26, 12, NULL, '010-1234-5678', '유저11', 'user11@email.com', '$2a$10$yUxTiZfXIQ0k.Btw6ewEcOkeFV.h0Jk64/0xL0QPHScNCW3CVj0SO', 'USER'),
      (NOW(), 27, 13, NULL, '010-1234-5678', '유저12', 'user12@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER'),
      (NOW(), 28, 14, NULL, '010-1234-5678', '유저13', 'user13@email.com', '$2a$10$mstjo6AH0wWQtkca2kAshO9NIiNaKj3aWkWWuGmgmECpEoUwUofvG', 'USER')
;



INSERT INTO board (
    view_count, board_id, created_at, member_id, updated_at, title, content, status
) VALUES
      -- 한참 지난 작성일 (2024-12-01)
      (11, 1, '2024-12-01 10:00:00.000000', 1, NULL, '오래된 작성', '이 글은 한참 전에 작성되었습니다.', 'COMPLETED'),
      -- 몇 시간 전 작성일 (3시간 전)
      (12, 2, DATE_SUB(NOW(), INTERVAL 3 HOUR), 2, NULL, '몇 시간 전 작성', '이 글은 3시간 전에 작성되었습니다.', 'COMPLETED'),
      -- 몇 분 전 작성일 (10분 전)
      (13, 3, DATE_SUB(NOW(), INTERVAL 10 MINUTE), 3, NULL, '몇 분 전 작성', '이 글은 10분 전에 작성되었습니다.', 'COMPLETED'),
      -- 한참 지난 작성일 (2024-12-01)
      (14, 4, NOW(), 4, NULL, '오래된 작성', '이 글은 한참 전에 작성되었습니다.', 'COMPLETED'),
      -- 몇 시간 전 작성일 (3시간 전)
      (15, 5, DATE_ADD(NOW(), INTERVAL 3 HOUR), 5, NULL, '몇 시간 후 수정', '이 글은 3시간 후에 수정되었습니다.', 'PENDING'),
      -- 몇 분 전 작성일 (10분 전)
      (16, 6, DATE_ADD(NOW(), INTERVAL 10 MINUTE), 1, NULL, '몇 분 후 수전', '이 글은 10분 후에 수정되었습니다.', 'PENDING'),
      -- 한참 지난 작성일 (2024-12-01)
      (17, 7, NOW(), 2, NULL, '테스트1', '테스트1로 작성되었습니다.', 'PENDING'),
      -- 몇 시간 전 작성일 (3시간 전)
      (18, 8, NOW(), 3, NULL, '테스트2', '테스트2로 작성되었습니다.', 'PENDING'),
      -- 몇 분 전 작성일 (10분 전)
      (19, 9, NOW(), 4, NULL, '테스트3', '테스트3로 작성되었습니다.', 'IMPORTANT'),
      -- 한참 지난 작성일 (2024-12-01)
      (20, 10, NOW(), 5, NULL, '테스트4', '테스트4로 작성되었습니다.', 'IMPORTANT'),
      -- 몇 시간 전 작성일 (3시간 전)
      (21, 11, NOW(), 1, NULL, '테스트5', '테스트5로 작성되었습니다.', 'IMPORTANT'),
      -- 몇 분 전 작성일 (10분 전)
      (22, 12, NOW(), 2, NULL, '테스트6', '테스트6로 작성되었습니다.', 'IMPORTANT')
;


# UPDATE member
# SET member_name = '관리자'
# WHERE member_id = 1;


INSERT INTO member (
    created_at, member_id, police_unit_id, office_phone, person_phone, member_name, email, password, role
) VALUES
      (NOW(), 1, 1, NULL, '010-4773-5141', '관리자', 'admin@email.com', '$2a$10$ZWm0tSioCSg1E8COGsNNN.gF.qzXGNfbcwiwAgIzgiXMw32j9jeWu', 'ADMIN'),
      (NOW(), 2, 2, NULL, '010-1234-5678', '유저1', 'user1@email.com', '$2a$10$yUxTiZfXIQ0k.Btw6ewEcOkeFV.h0Jk64/0xL0QPHScNCW3CVj0SO', 'USER'),
      (NOW(), 3, 3, NULL, '010-1234-5678', '유저2', 'user2@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER'),
      (NOW(), 4, 4, NULL, '010-1234-5678', '유저3', 'user3@email.com', '$2a$10$mstjo6AH0wWQtkca2kAshO9NIiNaKj3aWkWWuGmgmECpEoUwUofvG', 'USER'),
      (NOW(), 5, 5, NULL, '010-1234-5678', '유저4', 'user4@email.com', '$2a$10$ZWm0tSioCSg1E8COGsNNN.gF.qzXGNfbcwiwAgIzgiXMw32j9jeWu', 'USER'),
      (NOW(), 6, 6, NULL, '010-1234-5678', '유저5', 'user5@email.com', '$2a$10$yUxTiZfXIQ0k.Btw6ewEcOkeFV.h0Jk64/0xL0QPHScNCW3CVj0SO', 'USER'),
      (NOW(), 7, 7, NULL, '010-1234-5678', '유저6', 'user6@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER'),
      (NOW(), 8, 8, NULL, '010-1234-5678', '유저7', 'user7@email.com', '$2a$10$mstjo6AH0wWQtkca2kAshO9NIiNaKj3aWkWWuGmgmECpEoUwUofvG', 'USER'),
      (NOW(), 9, 9, NULL, '010-1234-5678', '유저8', 'user8@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER'),
      (NOW(), 10, 10, NULL, '010-1234-5678', '유저9', 'user9@email.com', '$2a$10$mstjo6AH0wWQtkca2kAshO9NIiNaKj3aWkWWuGmgmECpEoUwUofvG', 'USER'),
      (NOW(), 11, 11, NULL, '010-1234-5678', '유저10', 'user10@email.com', '$2a$10$ZWm0tSioCSg1E8COGsNNN.gF.qzXGNfbcwiwAgIzgiXMw32j9jeWu', 'USER'),
      (NOW(), 12, 12, NULL, '010-1234-5678', '유저11', 'user11@email.com', '$2a$10$yUxTiZfXIQ0k.Btw6ewEcOkeFV.h0Jk64/0xL0QPHScNCW3CVj0SO', 'USER'),
      (NOW(), 13, 13, NULL, '010-1234-5678', '유저12', 'user12@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER'),
      (NOW(), 14, 14, NULL, '010-1234-5678', '유저13', 'user13@email.com', '$2a$10$mstjo6AH0wWQtkca2kAshO9NIiNaKj3aWkWWuGmgmECpEoUwUofvG', 'USER'),
      (NOW(), 15, 15, NULL, '010-1234-5678', '유저14', 'user14@email.com', '$2a$10$ZWm0tSioCSg1E8COGsNNN.gF.qzXGNfbcwiwAgIzgiXMw32j9jeWu', 'USER'),
      (NOW(), 16, 16, NULL, '010-1234-5678', '유저15', 'user15@email.com', '$2a$10$yUxTiZfXIQ0k.Btw6ewEcOkeFV.h0Jk64/0xL0QPHScNCW3CVj0SO', 'USER'),
#       (NOW(), 17, 17, NULL, '010-1234-5678', '유저16', 'user16@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER'),
      (NOW(), 18, 18, NULL, '010-1234-5678', '유저17', 'user17@email.com', '$2a$10$mstjo6AH0wWQtkca2kAshO9NIiNaKj3aWkWWuGmgmECpEoUwUofvG', 'USER'),
      (NOW(), 19, 19, NULL, '010-1234-5678', '유저18', 'user18@email.com', '$2a$10$ZWm0tSioCSg1E8COGsNNN.gF.qzXGNfbcwiwAgIzgiXMw32j9jeWu', 'USER'),
      (NOW(), 20, 20, NULL, '010-1234-5678', '유저19', 'user19@email.com', '$2a$10$yUxTiZfXIQ0k.Btw6ewEcOkeFV.h0Jk64/0xL0QPHScNCW3CVj0SO', 'USER'),
      (NOW(), 21, 21, NULL, '010-1234-5678', '유저20', 'user20@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER'),
      (NOW(), 22, 22, NULL, '010-1234-5678', '유저21', 'user21@email.com', '$2a$10$mstjo6AH0wWQtkca2kAshO9NIiNaKj3aWkWWuGmgmECpEoUwUofvG', 'USER'),
      (NOW(), 23, 23, NULL, '010-1234-5678', '유저22', 'user22@email.com', '$2a$10$ZWm0tSioCSg1E8COGsNNN.gF.qzXGNfbcwiwAgIzgiXMw32j9jeWu', 'USER'),
      (NOW(), 24, 24, NULL, '010-1234-5678', '유저23', 'user23@email.com', '$2a$10$yUxTiZfXIQ0k.Btw6ewEcOkeFV.h0Jk64/0xL0QPHScNCW3CVj0SO', 'USER'),
      (NOW(), 25, 25, NULL, '010-1234-5678', '유저24', 'user24@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER'),
      (NOW(), 26, 26, NULL, '010-1234-5678', '유저25', 'user25@email.com', '$2a$10$mstjo6AH0wWQtkca2kAshO9NIiNaKj3aWkWWuGmgmECpEoUwUofvG', 'USER'),
      (NOW(), 27, 27, NULL, '010-1234-5678', '유저26', 'user26@email.com', '$2a$10$ZWm0tSioCSg1E8COGsNNN.gF.qzXGNfbcwiwAgIzgiXMw32j9jeWu', 'USER'),
      (NOW(), 28, 28, NULL, '010-1234-5678', '유저27', 'user27@email.com', '$2a$10$yUxTiZfXIQ0k.Btw6ewEcOkeFV.h0Jk64/0xL0QPHScNCW3CVj0SO', 'USER'),
      (NOW(), 29, 29, NULL, '010-1234-5678', '유저28', 'user28@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER'),
      (NOW(), 30, 30, NULL, '010-1234-5678', '유저29', 'user29@email.com', '$2a$10$mstjo6AH0wWQtkca2kAshO9NIiNaKj3aWkWWuGmgmECpEoUwUofvG', 'USER'),
      (NOW(), 31, 31, NULL, '010-1234-5678', '유저30', 'user30@email.com', '$2a$10$ZWm0tSioCSg1E8COGsNNN.gF.qzXGNfbcwiwAgIzgiXMw32j9jeWu', 'USER'),
      (NOW(), 32, 32, NULL, '010-1234-5678', '유저31', 'user31@email.com', '$2a$10$yUxTiZfXIQ0k.Btw6ewEcOkeFV.h0Jk64/0xL0QPHScNCW3CVj0SO', 'USER'),
      (NOW(), 33, 33, NULL, '010-1234-5678', '유저32', 'user32@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER'),
      (NOW(), 34, 34, NULL, '010-1234-5678', '유저33', 'user33@email.com', '$2a$10$mstjo6AH0wWQtkca2kAshO9NIiNaKj3aWkWWuGmgmECpEoUwUofvG', 'USER'),
      (NOW(), 35, 35, NULL, '010-1234-5678', '유저34', 'user34@email.com', '$2a$10$ZWm0tSioCSg1E8COGsNNN.gF.qzXGNfbcwiwAgIzgiXMw32j9jeWu', 'USER'),
      (NOW(), 36, 36, NULL, '010-1234-5678', '유저35', 'user35@email.com', '$2a$10$yUxTiZfXIQ0k.Btw6ewEcOkeFV.h0Jk64/0xL0QPHScNCW3CVj0SO', 'USER'),
      (NOW(), 37, 37, NULL, '010-1234-5678', '유저36', 'user36@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER'),
      (NOW(), 38, 38, NULL, '010-1234-5678', '유저37', 'user37@email.com', '$2a$10$mstjo6AH0wWQtkca2kAshO9NIiNaKj3aWkWWuGmgmECpEoUwUofvG', 'USER'),
      (NOW(), 39, 39, NULL, '010-1234-5678', '유저38', 'user38@email.com', '$2a$10$ZWm0tSioCSg1E8COGsNNN.gF.qzXGNfbcwiwAgIzgiXMw32j9jeWu', 'USER'),
      (NOW(), 40, 40, NULL, '010-1234-5678', '유저39', 'user39@email.com', '$2a$10$yUxTiZfXIQ0k.Btw6ewEcOkeFV.h0Jk64/0xL0QPHScNCW3CVj0SO', 'USER'),
      (NOW(), 41, 41, NULL, '010-1234-5678', '유저40', 'user40@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER');





















INSERT INTO member (
    created_at, member_id, police_unit_id, office_phone, person_phone, member_name, email, password, role
) VALUES
      (NOW(), 17, 17, NULL, '010-1234-5678', '전홍석', 'user16@email.com', '$2a$10$pdd2/vGGnjWLt2//2G9DaeATZpSzQLgehgd91DXk8C6VDz/nU6kgK', 'USER');