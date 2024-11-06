---- Update task_data.sql to correctly set list_id and card_id relationships
--
---- Insert test data for boards
--INSERT INTO boards (id, title, board_key) VALUES (1, 'Project Board', 'projectBoard123');
--
---- Insert test data for lists associated with boards
--INSERT INTO lists (id, title, board_id) VALUES (1, 'Backlog', 1);
--
---- Insert test data for cards associated with lists
--INSERT INTO cards (id, title, description, list_id) VALUES
--(1, 'Setup environment', 'Install necessary software and tools', 1);
--
---- Insert test data for tasks associated with cards
--INSERT INTO tasks (id, title, completed, card_id) VALUES
--(1, 'Install IDE', false, 1),
--(2, 'Set up version control', true, 1);


-- Create test boards
INSERT INTO boards (id, title, board_key) VALUES (1, 'Project Board', 'projectBoard123');

-- Create lists for the boards
INSERT INTO lists (id, title, board_id) VALUES (1, 'Backlog', 1), (2, 'In Progress', 1);

-- Create cards for the lists
INSERT INTO cards (id, title, description, list_id) VALUES
(1, 'Setup environment', 'Install necessary software and tools', 1),
(2, 'Develop features', 'Write the new features', 2);

-- Create tasks for the cards
INSERT INTO tasks (id, title, completed, card_id) VALUES
(1, 'Install IDE', false, 1),
(2, 'Set up version control', true, 1),
(3, 'Write user stories', false, 2);
