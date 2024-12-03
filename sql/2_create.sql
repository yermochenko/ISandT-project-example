CREATE TABLE "account" (
	"id"      BIGSERIAL PRIMARY KEY,
	"number"  TEXT      NOT NULL UNIQUE,
	"owner"   TEXT      NOT NULL,
	"balance" BIGINT    NOT NULL DEFAULT 0,
	"active"  BOOLEAN   NOT NULL DEFAULT TRUE
);

CREATE TABLE "transfer" (
	"id"          BIGSERIAL PRIMARY KEY,
	"sender_id"   BIGINT    REFERENCES "account" ON UPDATE RESTRICT ON DELETE RESTRICT,
	"receiver_id" BIGINT    REFERENCES "account" ON UPDATE RESTRICT ON DELETE RESTRICT,
	"sum"         BIGINT    NOT NULL,
	"date"        TIMESTAMP NOT NULL DEFAULT now(),
	"purpose"     TEXT
);