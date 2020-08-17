# Maybe Data

Maybe Data is a super simple, lightweight library that allows you to conditionally load recipes. 

Maybe Data will *only* attempt to load when the mod is installed (= a mod bundles Maybe Data), and when the condition at the top of the recipe file is met. Here's an example:

`resources/data/modid/mayberecipes/golden_apple.json`
```json
{
  "condition": {
    "modid": "moreapples"
  },
  "recipe": {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "###",
      "#X#",
      "###"
    ],
    "key": {
      "#": {
        "item": "minecraft:diamond"
      },
      "X": {
        "item": "minecraft:apple"
      }
    },
    "result": {
      "item": "minecraft:golden_apple"
    }
  }
}
```

This recipe will only load when Maybe Data is installed (More Apples / another mod might JIJ it), and when the `moreapples` modid exists. Simple, right?

Looking for more conditional control? Check out [LibCD](https://github.com/cottonmc/LibCD). This mod is MIT and built for the Fabric Loader.
