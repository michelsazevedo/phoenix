FROM gradle:jdk8

RUN apt-get update -qq && apt-get install -y -qq build-essential && apt-get clean

ENV APP_HOME /phoenix
RUN mkdir -p $APP_HOME
WORKDIR $APP_HOME

COPY . $APP_HOME
RUN gradle build --no-daemon
