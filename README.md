# Content Atom Model Definition

This is the Thrift definition of the Content Atom model, and the published versions of this repository are built from the autogenerated code in various languages.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.gu/content-atom-model-thrift/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.gu/content-atom-model-thrift)

## Adding a new atom type

In order for the scala code generated from the thrift definitions to be packaged correctly a scala namespace needs to be included. For example for the chart atom this would be:
`#@namespace scala com.gu.contentatom.thrift.atom.chart`


## How to make releases (maven and npm):

This repo uses [`gha-scala-library-release-workflow`](https://github.com/guardian/gha-scala-library-release-workflow)
to automate publishing releases (both full & preview releases) - see
[**Making a Release**](https://github.com/guardian/gha-scala-library-release-workflow/blob/main/docs/making-a-release.md).