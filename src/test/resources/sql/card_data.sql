INSERT INTO boards (id, title, board_key) VALUES
(1, 'Project Board', 'projectBoard123');

INSERT INTO lists (id, title, board_id) VALUES
(1, 'Backlog', 1);

INSERT INTO cards (id, title, description, list_id) VALUES
(1, 'Setup environment', 'Install necessary software and tools', 1),
(2, 'Define project scope', 'Determine project goals and deliverables', 1);
