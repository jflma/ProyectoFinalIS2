import { create, StateCreator } from "zustand";
import { devtools, persist, } from "zustand/middleware";

interface SearchState {
  query: string;

  setQuery: (query: string) => void
}

const storeApi: StateCreator<SearchState> = (set) => ({
  query:"",

  setQuery: (query) => {
    set({query})
  }
})

export const useSearchStore = create<SearchState>() (
  devtools(
    persist(
      storeApi,
      {name: 'search-storage'}
    )
  )
)