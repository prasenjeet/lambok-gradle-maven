// ── Enums ────────────────────────────────────────────────────────────────────

export enum Role {
  Admin = "ADMIN",
  Editor = "EDITOR",
  Viewer = "VIEWER",
}

export enum Priority {
  Low = 1,
  Medium = 2,
  High = 3,
}

// ── Interfaces ────────────────────────────────────────────────────────────────

export interface Address {
  street: string;
  city: string;
  country: string;
  postalCode?: string;
}

export interface User {
  id: number;
  name: string;
  email: string;
  role: Role;
  address?: Address;
  createdAt: Date;
}

// ── Type aliases & union/intersection types ───────────────────────────────────

export type ID = number | string;

export type Nullable<T> = T | null;

export type ReadonlyUser = Readonly<User>;

export type UserSummary = Pick<User, "id" | "name" | "email">;

export type PartialAddress = Partial<Address>;

// ── Discriminated union ───────────────────────────────────────────────────────

export type ApiResponse<T> =
  | { status: "success"; data: T }
  | { status: "error"; message: string; code: number };
