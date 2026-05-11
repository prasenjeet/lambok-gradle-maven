import { Priority } from "./types";

// ── Abstract base class ───────────────────────────────────────────────────────

abstract class BaseTask {
  readonly id: number;
  protected _title: string;
  private static nextId = 1;

  constructor(title: string) {
    this.id = BaseTask.nextId++;
    this._title = title;
  }

  get title(): string {
    return this._title;
  }

  abstract describe(): string;

  toString(): string {
    return `[${this.id}] ${this.describe()}`;
  }
}

// ── Concrete class with interface implementation ───────────────────────────────

interface Completable {
  complete(): void;
  isComplete(): boolean;
}

export class Task extends BaseTask implements Completable {
  private completed = false;

  constructor(
    title: string,
    public readonly priority: Priority = Priority.Medium,
    public readonly tags: string[] = []
  ) {
    super(title);
  }

  complete(): void {
    this.completed = true;
  }

  isComplete(): boolean {
    return this.completed;
  }

  describe(): string {
    const status = this.completed ? "✓" : "○";
    const priorityLabel = Priority[this.priority];
    return `${status} ${this._title} [${priorityLabel}]${this.tags.length ? ` #${this.tags.join(" #")}` : ""}`;
  }
}

// ── Class with static factory method ─────────────────────────────────────────

export class TaskList {
  private tasks: Task[] = [];

  static empty(): TaskList {
    return new TaskList();
  }

  static from(tasks: Task[]): TaskList {
    const list = new TaskList();
    list.tasks = [...tasks];
    return list;
  }

  add(task: Task): this {
    this.tasks.push(task);
    return this;
  }

  pending(): Task[] {
    return this.tasks.filter((t) => !t.isComplete());
  }

  completed(): Task[] {
    return this.tasks.filter((t) => t.isComplete());
  }

  byPriority(): Task[] {
    return [...this.tasks].sort((a, b) => b.priority - a.priority);
  }

  summary(): string {
    return `Total: ${this.tasks.length} | Done: ${this.completed().length} | Pending: ${this.pending().length}`;
  }
}
