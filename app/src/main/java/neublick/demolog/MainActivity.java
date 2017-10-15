package neublick.demolog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST = 1;

    private final String[] PERMISSIONS_APP_NEED = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean isNeedRequestPermission = false;
            for (String permission : PERMISSIONS_APP_NEED) {
                if (this.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    isNeedRequestPermission = true;
                    break;
                }
            }
            if (isNeedRequestPermission) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Yêu cầu cấp quyền");
                builder.setMessage("Ứng dụng xin cấp quyền để ghi lại lỗi đã xảy ra");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        requestPermissions(PERMISSIONS_APP_NEED, PERMISSION_REQUEST);
                    }
                });
                builder.show();
                return;
            }
        }
        init();
    }

    private void init() {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST:
                boolean isValid = true;
                if (grantResults.length > 0) {
                    for (int grant : grantResults) {
                        if (grant !=
                                PackageManager.PERMISSION_GRANTED) {
                            isValid = false;

                            break;
                        }
                    }
                    if (!isValid) {
                        finish();
                    } else {
                        init();
                    }
                }
                break;
            default:
        }
    }

}
