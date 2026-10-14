# NickBook

A small, production-friendly Paper plugin that recreates the supplied clickable written-book nickname flow.

## Features

- `/nick` opens the rank step in a written book.
- Clickable rank, skin, and name choices.
- Random name generation with retry.
- `/nick reset` restores the real username.
- Persistent per-player profiles in `plugins/NickBook/config.yml`.
- Configurable ranks and random names.
- Applies the nickname to the player's display name and tab list name.
- No external runtime dependencies.

## Build

Requires Java 21 and Gradle 8+.

```bash
gradle build
```

The plugin jar will be in `build/libs/`.

## GitHub Actions

Every push, pull request, or manual workflow run builds the plugin on Java 21 and uploads the jar as the `nickbook-plugin` artifact. Open the repository's **Actions** tab, select **Build NickBook**, then download the artifact from a successful run.

## Install

1. Build the jar.
2. Copy `nickbook-1.0.0.jar` into the server's `plugins/` directory.
3. Restart the Paper server.
4. Run `/nick` in-game.

## Important skin note

The flow stores the selected skin mode so it can be wired to a skin/disguise provider later. Applying real client-side skins requires a separate implementation such as a server-specific disguise system or packet/profile integration. The plugin intentionally does not bundle a third-party dependency.

## Commands

- `/nick` - open setup
- `/nick reset` - return to the real name
- `/nick rank <DEFAULT|VIP|VIP+|MVP|MVP+>`
- `/nick skin <normal|steve|random|previous>`
- `/nick name random`

## Permissions

- `nickbook.use` - default: true
- `nickbook.admin` - reserved for future admin controls
