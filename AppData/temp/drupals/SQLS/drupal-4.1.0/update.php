SELECT nid,type FROM node WHERE type = 'story' OR type = 'page' OR type = 'blog' OR type = 'forum' OR type = 'book';
-- SELECT * FROM $node->type WHERE nid = $node->nid;
SELECT n.nid, n.title FROM node n WHERE n.type = 'book' AND n.status = 0;
-- SELECT uid, name, jabber, drupal FROM users WHERE jabber != '' || drupal != '';
SELECT rid, name FROM role;
SELECT MAX(vid) AS high FROM vocabulary;
SELECT * FROM collection;
SELECT * FROM tag;
SELECT MAX(tid) AS high FROM term_data;
SELECT nid,attributes FROM node WHERE attributes != '';
SELECT name FROM tag WHERE attributes = '$node->attributes';
SELECT * FROM variable WHERE value <> 's';
SELECT MAX(nid) FROM node;
SELECT MAX(cid) FROM comments;
SELECT MAX(tid) FROM term_data;
SELECT MAX(cid) FROM comments;
