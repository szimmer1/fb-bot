# fb-bot

A scala server backend to respond to facebook bot requests

## Running Locally

#### Compile

Dependencies: `sbt` (> 0.11.0)

    git clone https://github.com/szimmer1/fb-bot.git && cd fb-bot
    sbt compile stage

#### Run with Foreman
Dependencies: `rvm`

    gem install bundler
    bundle install
    foreman start


#### Run with Heroku
Dependencies: [Heroku Toolbelt](https://toolbelt.heroku.com/)

    heroku local