USE platform;


DESC board;


DELETE FROM board;

DELETE FROM comment;

DELETE FROM image;


SELECT * FROM board;


SELECT * FROM comment;


SELECT * FROM notification;

SELECT * FROM verification_token;

DELETE FROM verification_token;


SELECT * FROM image;


INSERT INTO board (
    view_count, board_id, created_at, member_id, updated_at, title, content, status
) VALUES
      -- 한참 지난 작성일 (2024-12-01)
      (0, NULL, '2024-12-01 10:00:00.000000', 1, NULL, '오래된 작성', '이 글은 한참 전에 작성되었습니다.', 'GENERAL'),
      -- 몇 시간 전 작성일 (3시간 전)
      (0, NULL, DATE_SUB(NOW(), INTERVAL 3 HOUR), 2, NULL, '몇 시간 전 작성', '이 글은 3시간 전에 작성되었습니다.', 'PENDING'),
      -- 몇 분 전 작성일 (10분 전)
      (0, NULL, DATE_SUB(NOW(), INTERVAL 10 MINUTE), 3, NULL, '몇 분 전 작성', '이 글은 10분 전에 작성되었습니다.', 'COMPLETED');


insert into comment (board_id, comment_id, comment_id2, created_at, member_id, updated_at, content)
values
    (16, 23, 22, '2025-01-01 10:00:00.000000', 1, null,7772),
    (16, 24, 23, '2025-01-01 10:00:00.000000', 1, null,7773),
    (16, 25, 24, '2025-01-01 10:00:00.000000', 1, null,7774);



insert into comment (board_id, comment_id, comment_id2, created_at, member_id, updated_at, content)
values
    (16, 26, 25, '2025-01-01 10:00:00.000000', 1, null,7775),
    (16, 27, 26, '2025-01-01 10:00:00.000000', 1, null,7776),
    (16, 28, 27, '2025-01-01 10:00:00.000000', 1, null,7777);
