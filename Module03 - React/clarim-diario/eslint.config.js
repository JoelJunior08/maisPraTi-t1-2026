import js from '@eslint/js'
import globals from 'globals'
import reactHooks from 'eslint-plugin-react-hooks'
import reactRefresh from 'eslint-plugin-react-refresh'
import { defineConfig, globalIgnores } from 'eslint/config'

export default defineConfig([
  globalIgnores(['dist']),
  {
    files: ['**/*.{js,jsx}'],
    extends: [
      js.configs.recommended,
      reactHooks.configs.flat.recommended,
      reactRefresh.configs.vite,
    ],
    languageOptions: {
      globals: globals.browser,
      parserOptions: { ecmaFeatures: { jsx: true } },
    },
  },
  {
    // Arquivos de teste: habilita os globais do runner (describe, it, expect...).
    // Usamos os globais "jest", compatíveis com a API do Vitest.
    files: ['**/*.{test,spec}.{js,jsx}', '**/setupTests.js'],
    languageOptions: {
      globals: { ...globals.browser, ...globals.jest },
    },
  },
  {
    // Contextos exportam o Provider (componente) junto com o hook (ex.: useAuth).
    // É um padrão idiomático, então relaxamos a regra do Fast Refresh aqui.
    files: ['**/contexts/*.{js,jsx}'],
    rules: {
      'react-refresh/only-export-components': 'off',
    },
  },
])
