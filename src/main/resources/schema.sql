alter table if exists email_templates drop constraint if exists FKittot5qruvng1h0ppxnt8ol2s
alter table if exists emails_sent drop constraint if exists FKi1w75ss2ihr8cts719lee6dle
alter table if exists emails_sent drop constraint if exists FK9noipc32vx8g9lv7ctkcvhqom
alter table if exists emails_sent drop constraint if exists FKr9m6golo0oaeqiq4qm5ixts1s
alter table if exists non_profit drop constraint if exists FKb88w3gc2n9y0sj91ygnictue1
drop table if exists email_templates cascade
drop table if exists emails_sent cascade
drop table if exists foundation cascade
drop table if exists non_profit cascade
create table email_templates (is_active boolean default true not null, created_on timestamp(6) not null, updated_on timestamp(6) not null, foundation_id uuid not null, id uuid not null, body jsonb not null, subject jsonb not null, primary key (id))
create table emails_sent (is_sent boolean default true not null, created_on timestamp(6) not null, updated_on timestamp(6) not null, email_sent_id uuid not null, foundation_id uuid not null, id uuid not null, non_profit_id uuid not null, template_id uuid not null, body text not null, failure_reason varchar(255), subject text not null, to_email varchar(255) not null, primary key (id))
create table foundation (id uuid not null,created_on timestamp(6) not null, updated_on timestamp(6) not null,address varchar(255) not null,  email varchar(255) not null unique, name varchar(255) not null, primary key (id))
create table non_profit (is_active boolean default true not null, created_on timestamp(6) not null, updated_on timestamp(6) not null, foundation_id uuid not null, id uuid not null, address varchar(255) not null, email varchar(255) not null, name varchar(255) not null, primary key (id))
alter table if exists email_templates add constraint FKittot5qruvng1h0ppxnt8ol2s foreign key (foundation_id) references foundation
alter table if exists emails_sent add constraint FKi1w75ss2ihr8cts719lee6dle foreign key (foundation_id) references foundation
alter table if exists emails_sent add constraint FK9noipc32vx8g9lv7ctkcvhqom foreign key (non_profit_id) references non_profit
alter table if exists emails_sent add constraint FKr9m6golo0oaeqiq4qm5ixts1s foreign key (template_id) references email_templates
alter table if exists non_profit add constraint FKb88w3gc2n9y0sj91ygnictue1 foreign key (foundation_id) references foundation