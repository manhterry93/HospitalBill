package bkhn.et.hospitalbill.ui.newrecord;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.glxn.qrgen.android.QRCode;

import bkhn.et.hospitalbill.R;

import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/29/2018.
 */

@SuppressLint("ValidFragment")
public class RecordQRDialog extends DialogFragment {
    String mRecordId;
    ImageView mQrImage;
    TextView mTitle;

    public RecordQRDialog(String recordId) {
        mRecordId = recordId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_record_qr, container, false);
        mQrImage = view.findViewById(R.id.record_qr_image);
        mTitle = view.findViewById(R.id.title);
        mTitle.setMaxLines(4);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isNotNull(mRecordId)) {
            Bitmap image = QRCode.from(mRecordId).bitmap();
            mQrImage.setImageBitmap(image);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        getActivity().finish();
    }
}
