import { Pipe, PipeTransform } from '@angular/core';
import {MeasureType} from '../model/measure.type';

@Pipe({
  name: 'typeTranslate',
  standalone: true
})
export class TypeTranslatePipe implements PipeTransform {

  transform(value: unknown, ...args: unknown[]): unknown {
    switch (value) {
      case MeasureType.Main:
        return 'Põhimeetmed';
      case MeasureType.Standard:
        return 'Standardmeetmed';
      default:
        return 'Kõrgmeetmed';
    }
  }
}
