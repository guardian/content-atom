# Content Atom Model Definition

This is the Thrift definition of the Content Atom model, and the published versions of this repository are built from the autogenerated code in various languages.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.gu/content-atom-model-thrift/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.gu/content-atom-model-thrift)

## Adding a new atom type

In order for the scala code generated from the thrift definitions to be packaged correctly a scala namespace needs to be included. For example for the chart atom this would be:
`#@namespace scala com.gu.contentatom.thrift.atom.chart`

## How to release

Ensure you have the following installed on your machine:
 - `tsc` (`brew install typescript`)
 - `npm` (not sure! there are so many ways to install it)
 
Ensure you have an NPM account, part of the [@guardian](https://www.npmjs.com/org/guardian) org with a [configured token](https://docs.npmjs.com/creating-and-viewing-authentication-tokens)

```sbtshell
release // will release the scala / thrift projects
project typescriptClasses
release 1.0.0 // you have to specify the version again
```

This will release 3 artifacts to Maven Central:

* `content-atom-model-thrift-$version.jar` contains only the Thrift files
* `content-atom-model_2.13-$version.jar` contains the Thrift files and Scrooge-generated Scala 2.13 classes
* `content-atom-model_2.12-$version.jar` contains the Thrift files and Scrooge-generated Scala 2.12 classes
* `content-atom-model_2.11-$version.jar` contains the Thrift files and Scrooge-generated Scala 2.11 classes

You will need a PGP key and Sonatype credentials. See [here](https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html) and [here](https://docs.google.com/document/d/1M_MiE8qntdDn97QIRnIUci5wdVQ8_defCqpeAwoKY8g/edit#heading=h.r815791vmxv5) for some helpful guides.

To cross release locally use

```
$ sbt '+publishLocal'
```