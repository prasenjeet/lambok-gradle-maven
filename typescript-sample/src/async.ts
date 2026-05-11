import { User, Role, ApiResponse } from "./types";

// ── Simulated async data fetching ─────────────────────────────────────────────

function delay(ms: number): Promise<void> {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

async function fetchUser(id: number): Promise<ApiResponse<User>> {
  await delay(50);

  if (id <= 0) {
    return { status: "error", message: "Invalid user ID", code: 400 };
  }

  const user: User = {
    id,
    name: `User ${id}`,
    email: `user${id}@example.com`,
    role: id === 1 ? Role.Admin : Role.Viewer,
    createdAt: new Date(),
  };

  return { status: "success", data: user };
}

async function fetchUsers(ids: number[]): Promise<User[]> {
  const results = await Promise.allSettled(ids.map(fetchUser));

  return results
    .filter(
      (r): r is PromiseFulfilledResult<ApiResponse<User>> =>
        r.status === "fulfilled" && r.value.status === "success"
    )
    .map((r) => (r.value as { status: "success"; data: User }).data);
}

// ── Async generator ───────────────────────────────────────────────────────────

async function* generateUserIds(start: number, end: number): AsyncGenerator<number> {
  for (let i = start; i <= end; i++) {
    await delay(10);
    yield i;
  }
}

export async function runAsyncDemo(): Promise<void> {
  console.log("\n── Async / Await Demo ──────────────────────────────────────");

  // Single fetch with discriminated union handling
  const response = await fetchUser(1);
  if (response.status === "success") {
    console.log(`Fetched: ${response.data.name} (${response.data.role})`);
  }

  const errorResponse = await fetchUser(-1);
  if (errorResponse.status === "error") {
    console.log(`Error ${errorResponse.code}: ${errorResponse.message}`);
  }

  // Parallel fetch
  const users = await fetchUsers([1, 2, 3]);
  console.log(`Parallel fetch — got ${users.length} users`);

  // Async iteration
  const collected: number[] = [];
  for await (const id of generateUserIds(10, 12)) {
    collected.push(id);
  }
  console.log(`Async generator yielded IDs: ${collected.join(", ")}`);
}
