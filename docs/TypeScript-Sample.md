# TypeScript Sample

A sample TypeScript project showcasing core language features: types, generics, classes, async/await, and modern syntax.

## Prerequisites

- Node.js 18+
- npm 9+

## Project Structure

```
typescript-sample/
├── package.json
├── tsconfig.json
└── src/
    ├── index.ts      # Entry point — runs all demos
    ├── types.ts      # Enums, interfaces, utility types, discriminated unions
    ├── generics.ts   # Generic functions, Store<T> class
    ├── classes.ts    # Abstract classes, access modifiers, static factories
    └── async.ts      # async/await, Promise.allSettled, async generators
```

## Install & Run

```bash
cd typescript-sample

# Install dependencies
npm install

# Run directly with ts-node (no build step)
npm run dev

# Compile then run
npm run build
npm start

# Clean compiled output
npm run clean
```

## Features Demonstrated

### Types & Interfaces (`types.ts`)

- String and numeric enums (`Role`, `Priority`)
- Interfaces with optional fields (`User`, `Address`)
- Utility types: `Readonly<T>`, `Pick<T,K>`, `Partial<T>`
- Union, intersection, and discriminated union types (`ApiResponse<T>`)

### Generics (`generics.ts`)

- Generic `identity`, `first`, `last`, and `groupBy` functions
- Constrained generics with `maxBy`
- Generic `Store<T>` class backed by a `Map`

### Classes (`classes.ts`)

- Abstract base class with `private`, `protected`, and `readonly` modifiers
- Getters and method chaining
- Interface implementation (`Completable`)
- Static factory methods (`TaskList.empty()`, `TaskList.from()`)

### Async (`async.ts`)

- `async`/`await` with typed return values
- Discriminated union API responses (`ApiResponse<T>`)
- `Promise.allSettled` for parallel fetching
- Async generators (`async function*`) and `for await...of`

### Modern Syntax (`index.ts`)

- Destructuring with defaults and aliases
- Spread operator for arrays and objects
- Optional chaining (`?.`) and nullish coalescing (`??`)

## TypeScript Configuration

Key `tsconfig.json` settings:

| Option | Value | Reason |
|--------|-------|--------|
| `target` | `ES2020` | Enables async generators and modern JS features |
| `strict` | `true` | Full type safety — no implicit `any`, strict null checks |
| `outDir` | `./dist` | Compiled output directory |
| `sourceMap` | `true` | Source maps for debugging |
| `declaration` | `true` | Emits `.d.ts` type declaration files |

## Related Pages

- [Home](Home.md)
