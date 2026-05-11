// ── Generic utilities ─────────────────────────────────────────────────────────

export function identity<T>(value: T): T {
  return value;
}

export function first<T>(items: T[]): T | undefined {
  return items[0];
}

export function last<T>(items: T[]): T | undefined {
  return items[items.length - 1];
}

export function groupBy<T, K extends string | number | symbol>(
  items: T[],
  keyFn: (item: T) => K
): Record<K, T[]> {
  return items.reduce(
    (acc, item) => {
      const key = keyFn(item);
      if (!acc[key]) acc[key] = [];
      acc[key].push(item);
      return acc;
    },
    {} as Record<K, T[]>
  );
}

// ── Generic class: typed in-memory store ─────────────────────────────────────

export class Store<T extends { id: number | string }> {
  private items: Map<T["id"], T> = new Map();

  add(item: T): void {
    this.items.set(item.id, item);
  }

  get(id: T["id"]): T | undefined {
    return this.items.get(id);
  }

  remove(id: T["id"]): boolean {
    return this.items.delete(id);
  }

  getAll(): T[] {
    return Array.from(this.items.values());
  }

  count(): number {
    return this.items.size;
  }
}

// ── Constrained generics ──────────────────────────────────────────────────────

export function maxBy<T>(items: T[], selector: (item: T) => number): T | undefined {
  if (items.length === 0) return undefined;
  return items.reduce((best, item) =>
    selector(item) > selector(best) ? item : best
  );
}
