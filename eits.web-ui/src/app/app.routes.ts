import { Routes } from '@angular/router';
import {HomeComponent} from './components/home/home.component';
export const AppPaths = {
  measures: {
    root: 'measures',
  }
};

export const routes: Routes = [
  { path: '', redirectTo: AppPaths.measures.root, pathMatch: 'full' },
  {
    path: AppPaths.measures.root,
    component: HomeComponent,
  },
];
