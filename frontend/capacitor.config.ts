import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.kpekala.habitgame',
  appName: 'HabitGame',
  webDir: 'dist/frontend',
  server: {
    cleartext: true, 
    androidScheme: 'http'
  }
};

export default config;
