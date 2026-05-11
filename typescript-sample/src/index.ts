import { Role, Priority } from "./types";
import { identity, first, last, groupBy, Store, maxBy } from "./generics";
import { Task, TaskList } from "./classes";
import { runAsyncDemo } from "./async";

// ── 1. Types & Interfaces demo ────────────────────────────────────────────────

function typesDemo(): void {
  console.log("── Types & Interfaces ──────────────────────────────────────");

  const roles = Object.values(Role);
  console.log("Roles:", roles.join(", "));

  const priorities: Record<string, number> = {
    Low: Priority.Low,
    Medium: Priority.Medium,
    High: Priority.High,
  };
  console.log("Priorities:", JSON.stringify(priorities));
}

// ── 2. Generics demo ──────────────────────────────────────────────────────────

function genericsDemo(): void {
  console.log("\n── Generics ────────────────────────────────────────────────");

  console.log("identity(42):", identity(42));
  console.log("identity('hello'):", identity("hello"));

  const nums = [3, 1, 4, 1, 5, 9, 2, 6];
  console.log("first:", first(nums), "| last:", last(nums));
  console.log("maxBy:", maxBy(nums, (n) => n));

  const words = ["apple", "avocado", "banana", "blueberry", "cherry"];
  const grouped = groupBy(words, (w) => w[0]);
  for (const [letter, group] of Object.entries(grouped)) {
    console.log(`  ${letter}: ${group.join(", ")}`);
  }

  // Generic Store
  const store = new Store<{ id: number; value: string }>();
  store.add({ id: 1, value: "alpha" });
  store.add({ id: 2, value: "beta" });
  store.add({ id: 3, value: "gamma" });
  console.log(`Store has ${store.count()} items. Get #2:`, store.get(2)?.value);
  store.remove(2);
  console.log(`After remove, count: ${store.count()}`);
}

// ── 3. Classes demo ───────────────────────────────────────────────────────────

function classesDemo(): void {
  console.log("\n── Classes ─────────────────────────────────────────────────");

  const list = TaskList.empty();

  const t1 = new Task("Design database schema", Priority.High, ["backend", "db"]);
  const t2 = new Task("Write unit tests", Priority.Medium, ["testing"]);
  const t3 = new Task("Update documentation", Priority.Low, ["docs"]);
  const t4 = new Task("Deploy to staging", Priority.High, ["devops"]);

  list.add(t1).add(t2).add(t3).add(t4);

  t1.complete();
  t2.complete();

  list.byPriority().forEach((t) => console.log(" ", t.toString()));
  console.log(list.summary());
}

// ── 4. Destructuring, spread, optional chaining ───────────────────────────────

function modernSyntaxDemo(): void {
  console.log("\n── Modern Syntax ───────────────────────────────────────────");

  const user = { id: 1, name: "Alice", address: { city: "Berlin", country: "DE" } };

  const { name, address: { city, country } = {} } = user;
  console.log(`User: ${name}, City: ${city ?? "N/A"}, Country: ${country ?? "N/A"}`);

  const base = [1, 2, 3];
  const extended = [...base, 4, 5];
  console.log("Spread array:", extended);

  const settings = { theme: "dark", lang: "en" };
  const overrides = { lang: "de", fontSize: 14 };
  const merged = { ...settings, ...overrides };
  console.log("Merged object:", merged);

  // Nullish coalescing & optional chaining
  const maybeUser: typeof user | null = null;
  const city2 = maybeUser?.address?.city ?? "Unknown";
  console.log("Optional chain result:", city2);
}

// ── Entry point ───────────────────────────────────────────────────────────────

async function main(): Promise<void> {
  console.log("=== TypeScript Sample Project ===\n");

  typesDemo();
  genericsDemo();
  classesDemo();
  modernSyntaxDemo();
  await runAsyncDemo();

  console.log("\n=== Done ===");
}

main().catch(console.error);
