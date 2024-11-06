INSERT INTO boards (id, title, board_key) VALUES (1, 'Project Board', 'projectBoard123');

INSERT INTO lists (id, title, board_id) VALUES (1, 'Backlog', 1), (2, 'In Progress', 1);

INSERT INTO cards (id, title, description, list_id) VALUES
(1, 'Setup environment', 'Install necessary software and tools', 1),
(2, 'Develop features', 'Write the new features', 2);

INSERT INTO tasks (id, title, completed, card_id) VALUES
(1, 'Install IDE', false, 1),
(2, 'Set up version control', true, 1),
(3, 'Write user stories', false, 2);
