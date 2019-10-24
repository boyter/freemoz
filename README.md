# Freemoz
A spiritual sucessor to dmoz.org

[![CircleCI](https://circleci.com/gh/boyter/freemoz.svg?style=svg)](https://circleci.com/gh/boyter/freemoz)
[![Go Report Card](https://goreportcard.com/badge/github.com/boyter/freemoz)](https://goreportcard.com/report/github.com/boyter/freemoz)
[![Scc Count Badge](https://sloc.xyz/github/boyter/freemoz/?category=code)](https://github.com/boyter/freemoz/)
[![Scc Count Badge](https://sloc.xyz/github/boyter/freemoz/?category=blanks)](https://github.com/boyter/freemoz/)
[![Scc Count Badge](https://sloc.xyz/github/boyter/freemoz/?category=lines)](https://github.com/boyter/freemoz/)
[![Scc Count Badge](https://sloc.xyz/github/boyter/freemoz/?category=comments)](https://github.com/boyter/freemoz/)
[![Scc Count Badge](https://sloc.xyz/github/boyter/freemoz/?category=cocomo)](https://github.com/boyter/freemoz/)



DONE
- Ordered domain freemoz.org
- Download existing dmoz data
- Download slashtag data https://github.com/blekko/slashtag-data.git
- Write down editorial guidelines / code of conduct / values probably based on http://contributor-covenant.org/version/1/4/
- Create basic application

TODO
- Establish goals
- Investigate https://github.com/kremso/dmoz-parser for parsing existing dmoz data
- Build data converters
- Add letsencrypt https cert https://certbot.eff.org/#ubuntuxenial-nginx
- Setup website
- Setup simple landing page
- Allow submissions IN PROGRESS
- Add basic admin process to approve submissions needs to do the following,
  - Pop the next available submission from the queue and lock it for period of time
  - Allow editing (if edit it goes back into the queue)
  - Allow searching for category to place it
  - Allow searching for existing site and the like
- Investigate pulling infromation from https://github.com/sindresorhus/awesome
- Automate refresh of SSL cert
- "Arts Movies" search on:  AOL - Ask - Bing - DuckDuckGo - Gigablast - Google - ixquick - Yahoo - Yandex - Yippy add to bottom of search

GOALS
- A new version of DMOZ
- Data to be exported in a way that is simple for anyone to import/use update
- Project must be forkable with minimum of effort
- Self contained. Must be able to stand up a new instance of freemoz with minimum of effort

DECISIONS
- Use https://github.com/go-bindata/go-bindata to allow single file deployment (with no content)

### License

All code to be released under GNU GENERAL PUBLIC LICENSE Version 3

[![CC0](http://mirrors.creativecommons.org/presskit/buttons/88x31/svg/cc-zero.svg)](https://creativecommons.org/publicdomain/zero/1.0/)

All content should be released under a Creative Commons License.

### Self Contained

To make freemoz self contained the following assumptions/solutions are going to be made.

 - Freemoz is a read heavy application. Delayed updates from edits are acceptable.
 - No reliance on external services. As such it will do its own user management.

Going to use Go as the language. This means it will be possible to run Freemoz on any machine. Going to use SQLite as the database. This means that there is no external database requirement, however in the interests of scale must ensure that it is possible to switch this out at a later date.
