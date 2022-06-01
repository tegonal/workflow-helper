FROM openjdk:17-alpine

RUN apk add --no-cache bash
RUN apk add --no-cache git

CMD "/bin/bash"

COPY /scripts/parse-args.sh /usr/bin/parse-args.sh
COPY /scripts/todo-checker.sh /usr/bin/todo-checker
COPY ./target/scala-*/workflow-helper-assembly-*.jar /usr/bin/workflow-helper.jar

