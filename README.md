# Content Atom Model Definition

This is the Thrift definition of the Content Atom model, and the published
versions of this repository are built from the autogenerated code in various
languages.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.gu/content-atom-model/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.gu/content-atom-model)

## Thrift

You need to have [french-thrift 0.9.3](https://github.com/guardian/french-thrift) installed locally.

## How to release

```
$ sbt release
```

This will release 2 artifacts to Maven Central:

* `content-atom-model_2.11-$version.jar` contains the Thrift files and Scrooge-generated Scala 2.11 classes
* `content-atom-model-thrift-$version.jar` contains only the Thrift files

You will need a PGP key and Sonatype credentials.  

### JavaScript

The JS library should be published to npmjs, see:

https://www.npmjs.com/package/publish

and `npm help publish`.

But in summary:

1. Bump the version number in `package.json`

2. Make sure you have built the latest version of the JS library with `sbt compile`

3. If you've added another type, be sure to add it to `js/main.js`

4. Then, `npm publish` in the same directory as `package.json`
