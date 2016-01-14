# Adding a new content atom type

This document describes the process for creating a new content atom
type for your application, and making it available to other tools for
embedding.

The pre-requisite is that you must have decided (and probably
implemented) the internal model your appliation's data type.

You should then create a Thrift model which describes your project's
commitment to an specific structure whenever its content is
distributed externally for consumption by other tool (e.g. for reading
by Porter and indexing into CAPI).

## Creating your model

Begin by creating a new directory within
`thrift/atoms/<content-atom-type>` and create you Thrift model here
using Thrift's [IDL](https://thrift.apache.org/docs/idl). Naturally
you can use the other content atom types as a starting point here.

If you want to use any of the types in `shared.thrift`, include the
file and reference the types with the prefix `shared.`

```
include "../shared.thrift"
...
struct Example {
   1: required shared.OpaqueJson data
}
```

Add your type to the `union` `ContentAtom` in `contentatom.thrift` to
make it usable as one of the types that can be placed into
`ContentAtomEvent`s and similar.

## Building and publishing your changes

The `root` project aggregates all of the other projects, and we are
using the [sbt-release](https://github.com/sbt/sbt-release) plugin so
you should be able to publish everything in one go.

### Preliminary configuration

The first step is to create an account on
[SonaType](https://oss.sonatype.org) and then
[log a ticket](https://issues.sonatype.org/) to get added to the
Guardian profile.

Once that is done, create a file `~/.sbt/<version>/sonatype.sbt` with
your credentials in, like this:

```
credentials += Credentials("Sonatype Nexus Repository Manager",
                           "oss.sonatype.org",
                           "USERNAME",
                           "PASSWORD")
```

You will also need to create a PGP key that you can sign the release
with. Add the secret key to `~/.sbt/gpg/secring.asc` and upload the
public key to a key-server. There's more details in the `sbt-pgp`
plugin [documentation](http://www.scala-sbt.org/sbt-pgp/) and on
SonaType's page about
[using PGP signatures](http://central.sonatype.org/pages/working-with-pgp-signatures.html).

### Performing the release

After all that is in place, you should then be able to just type `sbt
release` from the commmand line (or just `release` from sbt within the
root project).

This will prompt you to bump the version number and it will tag the
repo etc. Checkout the `sbt-release` plugin docs linked above for more
details.

## TODO

* packaging it up and testing it.

* I wonder if I could/should create some kind of content-atom
    debugger?
