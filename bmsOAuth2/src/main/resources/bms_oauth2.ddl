create database if not exists bms_oauth2;
use bms_oauth2;
-- used in tests that use HSQL
create table if not exists oauth_client_details (
client_id VARCHAR(128) PRIMARY KEY,
resource_ids VARCHAR(128),
client_secret VARCHAR(128),
client_secret_decode VARCHAR(128),
scope VARCHAR(128),
authorized_grant_types VARCHAR(128),
web_server_redirect_uri VARCHAR(128),
authorities VARCHAR(128),
access_token_validity INTEGER,
refresh_token_validity INTEGER,
additional_information VARCHAR(4096),
autoapprove VARCHAR(128)
);
create table if not exists oauth_client_token (
token_id VARCHAR(128),
token BLOB,
authentication_id VARCHAR(128) PRIMARY KEY,
user_name VARCHAR(128),
client_id VARCHAR(128)
);
create table if not exists oauth_access_token (
token_id VARCHAR(128),
token BLOB,
authentication_id VARCHAR(128) PRIMARY KEY,
user_name VARCHAR(128),
client_id VARCHAR(128),
authentication BLOB,
refresh_token VARCHAR(128)
);
create table if not exists oauth_refresh_token (
token_id VARCHAR(128),
token BLOB,
authentication BLOB
);
create table if not exists oauth_code (
code VARCHAR(128), authentication BLOB
);
create table if not exists oauth_approvals (
userId VARCHAR(128),
clientId VARCHAR(128),
scope VARCHAR(128),
status VARCHAR(10),
expiresAt TIMESTAMP,
lastModifiedAt TIMESTAMP
);
-- customized oauth_client_details table
create table if not exists ClientDetails (
appId VARCHAR(128) PRIMARY KEY,
resourceIds VARCHAR(128),
appSecret VARCHAR(128),
scope VARCHAR(128),
grantTypes VARCHAR(128),
redirectUrl VARCHAR(128),
authorities VARCHAR(128),
access_token_validity INTEGER,
refresh_token_validity INTEGER,
additionalInformation VARCHAR(4096),
autoApproveScopes VARCHAR(128)
);

insert into oauth_client_details (client_id,scope,client_secret,client_secret_decode,authorized_grant_types,web_server_redirect_uri)values ('password_client','all','$2a$10$NdNNn2kcTo06LQwO.MwLhOwRw36w5j0XeQNQSPOpmf16l1c6GJFW6','server','authorization_code,client_credentials,password,refresh_token,implicit','');
insert into oauth_client_details (client_id,scope,client_secret,client_secret_decode,authorized_grant_types,web_server_redirect_uri)values ('client_credentials','all','$2a$10$NdNNn2kcTo06LQwO.MwLhOwRw36w5j0XeQNQSPOpmf16l1c6GJFW6','server','authorization_code,client_credentials,password,refresh_token,implicit','');
insert into oauth_client_details (client_id,scope,client_secret,client_secret_decode,authorized_grant_types,web_server_redirect_uri)values ('authorization_code','all','$2a$10$NdNNn2kcTo06LQwO.MwLhOwRw36w5j0XeQNQSPOpmf16l1c6GJFW6','server','authorization_code,refresh_token','');