# Freemoz
A spiritual sucessor to dmoz.org

[![Build Status](https://circleci.com/gh/boyter/freemoz/tree/master.svg?style=shield&circle-token=:circle-token)](https://circleci.com/gh/boyter/freemoz/tree/master)

DONE
- Ordered domain
- Setup simple landing page
- Download existing dmoz data
- Download slashtag data https://github.com/blekko/slashtag-data.git
- Write down editorial guidelines / code of conduct / values probably based on http://contributor-covenant.org/version/1/4/

TODO
- Add letsencrypt https cert
- Create basic application
- Setup website
- Establish goals
- Investigate pulling infromation from https://github.com/sindresorhus/awesome

GOALS
- A new version of DMOZ
- Data to be exported in a way that is simple for anyone to import/use update
- Project must be forkable with minimum of effort
- Self contained. Must be able to stand up a new instance of freemoz with minimum of effort


### License

All code to be released under GNU GENERAL PUBLIC LICENSE Version 3

[![CC0](http://mirrors.creativecommons.org/presskit/buttons/88x31/svg/cc-zero.svg)](https://creativecommons.org/publicdomain/zero/1.0/)

All content should be released under a Creative Commons License.

### Self Contained

To make freemoz self contained the following assumptions/solutions are going to be made.

Freemoz is a read heavy application. Delayed updates from edits are acceptable.

Going to use Java 8 as the runtime envrionment using Spark (http://sparkjava.com/) framework. This means
it will be possible to run Freemoz on any machine capable of running Java. Going to use SQLite
as the database. This means that there is no external database requirement, however in the interests
of scale must ensure that it is possible to switch this out at a later date.
