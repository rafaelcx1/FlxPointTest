FROM node:18

WORKDIR /usr/src/app

RUN npm install -g json-server

COPY db.json /usr/src/app/db.json

EXPOSE 3000

ENTRYPOINT ["json-server", "--watch", "db.json", "--host", "0.0.0.0", "--port", "3000"]