import { Routes } from '@angular/router';
import {HomePage} from './pages/home/home.page';
import {SystemBootingPage} from './pages/system-booting/system-booting.page';
import {SystemHealthGuard} from './guards/system-health.guard';
export const AppPaths = {
  measures: {
    root: 'measures',
  },
  system:{
    booting: 'system-booting'
  }
};

export const routes: Routes = [
  { path: '', redirectTo: AppPaths.measures.root, pathMatch: 'full' },
  {
    path: AppPaths.measures.root,
    component: HomePage,
    canActivate: [SystemHealthGuard]
  },
  {
    path: AppPaths.system.booting,
    component: SystemBootingPage,
  }
];
