# clj-pages

Static page generator using markdown for posts and hiccup for templates.

## Usage

1) Make sure you have lein installed

2) Clone this repo

3) Write posts in the "_post" directory using .md extension

4) Customize views.clj if you want to

5) lein run to generate the pages to out

## Links

Markdown (for posts): http://daringfireball.net/projects/markdown/

Hiccup (for pages): https://github.com/weavejester/hiccup

Jekyll (inspiration): https://github.com/mojombo/jekyll/wiki

# TODOs

1. add clj-yaml support in posts a la jekyll
2. rendering engines for filetypes
3. bootstrap support / sane templates
4. lein template / plugin
5. code syntax, use markdowns backtick code tag
6. permlinks? / extensions
7. _static dir contains images, css, and js helper functions for includes
8. auto push with webdriver-ish for github etc
9. config file for dirs etc
10. sane defaults in general (about index list two categories drafts)
11. custom routes rendering dirs
12. yaml password protection static impossible## License

Copyright © 2012 oskarth and zachallaun

Distributed under the Eclipse Public License, the same as Clojure.
