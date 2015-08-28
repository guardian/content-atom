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

## TODO

* adding to the `build.sh` file to make sure that your library gets
    built with the required languages.

* packaging it up and testing it.

* I wonder if I could/should create some kind of content-atom
    debugger?
