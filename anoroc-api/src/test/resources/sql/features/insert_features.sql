INSERT INTO features (id,name,content,xpath,created_at,updated_at,feature_type,application_id)
VALUES (1,'Bing search feature',
'Feature: Searching on bing\r\n  Scenario Outline: Search for some terms on bing\r\n Given I OPEN [url] for bing',
'{"url": "http://www.bing.com"}'
,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'ANOROC',1);

INSERT INTO features (id,name,content,xpath,created_at,updated_at,feature_type,application_id)
VALUES (2,'Bing search feature',
'Feature: Searching on bing\r\n  Scenario Outline: Search for some terms on bing\r\n Given I OPEN [url] for bing',
'{"url": "http://www.bing.com"}'
,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'ANOROC',1);