#!/bin/bash
set -e

# Wait for PostgreSQL to start
until pg_isready -h "postgres-compose" -p "5432" -U "postgres"; do
  echo "Waiting for PostgreSQL to start..."
  sleep 2
done

# Execute the SQL script
psql -U postgres -f /docker-entrypoint-initdb.d/init.sql

exec "$@"